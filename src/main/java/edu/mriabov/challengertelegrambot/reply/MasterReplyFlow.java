package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.PublicButtonsMessages;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ButtonsUtils.buildMessageWithKeyboard;
import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.sendKeyboard;


@Component
public class MasterReplyFlow {

    //ids are to be labled: [x]00 for separate menu buttons, 0[x]0 for features in menu, 00[x] for subfeatures.
    //todo constants instead of methods and a constructor

    private final ReplyBuilderService replyBuilderService;
//    private final ReplyFlow welcomeFlow = welcome();
    private final ReplyFlow mainMenuFlow;
    private final ReplyFlow myChallengesFlow;
    private final ReplyFlow setGoalFlow;
    private final ReplyFlow selectDifficultyFlow;
    private final ReplyFlow skipChallengeFlow;
    private final ReplyFlow markAsCompletedFlow ;
    @Autowired
    public MasterReplyFlow(ReplyBuilderService replyBuilderService) {
        this.replyBuilderService = replyBuilderService;

        mainMenuFlow = mainMenu();
        myChallengesFlow = myChallengesFlow();
        setGoalFlow = setGoal();
        selectDifficultyFlow = selectDifficulty();
        skipChallengeFlow = skipChallenge();
        markAsCompletedFlow = markAsCompleted();
    }

    public ReplyFlow welcome() {
        return ReplyFlow.builder(TelegramBot.database, 1)
                .next(replyBuilderService.buildSimpleFlow(2, ReceivedMessages.ON_START_NEW_USER_NO, mainMenuFlow))
                .next(replyBuilderService.buildSimpleFlow(3, ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                replyBuilderService.buildSimpleFlow(4, ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenuFlow),
                                replyBuilderService.buildSimpleFlow(5, ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                        replyBuilderService.buildSimpleFlow(6, ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenuFlow)
                                ))
                        )
                )
                )
                .build();
    }

    public ReplyFlow mainMenu() {
        return replyBuilderService.buildSimpleFlow(100,ReceivedMessages.MAIN_MENU,List.of(
                myChallengesFlow,
                        selectDifficultyFlow
        )
        );
    }

    //MainMenuFlow
    private ReplyFlow myChallengesFlow() {
        return replyBuilderService.buildSimpleFlow(210, ReceivedMessages.MENU_MY_CHALLENGES, List.of(
                        markAsCompletedFlow,
                        setGoalFlow,
                        skipChallengeFlow
                )
        );
    }

    private ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(220, ReceivedMessages.SET_GOAL,
                List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isCancel)
                                .action((baseAbilityBot, update) -> baseAbilityBot.silent().execute(buildMessageWithKeyboard(update.getMessage().getChatId(), Buttons.SET_GOAL)))
                                .next(myChallengesFlow)
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> !TelegramUtils.isCancel(update))
                                .action(((baseAbilityBot, update) -> {
                                    //todo db operation+if have enough balance
                                    baseAbilityBot.silent().execute(buildMessageWithKeyboard(update.getMessage().getChatId(), Buttons.SET_GOAL));
                                }))
                                .next(myChallengesFlow)
                                .build())
        );
    }

    private ReplyFlow skipChallenge() {
        return replyBuilderService.buildSimpleFlow(230, ReceivedMessages.SKIP_CHALLENGES, List.of(
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isCancel)
                        .action((baseAbilityBot, update) -> sendKeyboard(update, baseAbilityBot, Buttons.SKIP_CHALLENGES))
                        .next(myChallengesFlow)
                        .build(),
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(update -> !TelegramUtils.isCancel(update))
                        .action((baseAbilityBot, update) -> {
                            //todo dbOperation
                            sendKeyboard(update, baseAbilityBot, Buttons.SKIP_CHALLENGES);
                        })
                        .next(myChallengesFlow)
                        .build()
        ));
    }

    private ReplyFlow markAsCompleted() {
        return replyBuilderService.buildSimpleFlow(240, ReceivedMessages.MARK_CHALLENGE_AS_COMPLETED,
                List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> update.getMessage().getText().equals(PublicButtonsMessages.CANCEL.getText()))
                                .action((baseAbilityBot, update) -> baseAbilityBot.silent().execute(
                                        buildMessageWithKeyboard(update.getMessage().getChatId(),
                                                Buttons.MARK_CHALLENGE_AS_COMPLETED)))
                                .next(myChallengesFlow)
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> !update.getMessage().getText().equals(Buttons.cancelMessage))
                                .action((baseAbilityBot, update) -> {
                                    //todo save!!!
                                    baseAbilityBot.silent().send("Success!", update.getMessage().getChatId());
                                })
                                .next(myChallengesFlow)
                                .build()
                )
        );
    }

    //ChallengeCreateFlow
    private ReplyFlow selectDifficulty() {
        Challenge challenge = new Challenge();
        return replyBuilderService.buildSimpleFlow(310, ReceivedMessages.MENU_CHALLENGE_YOUR_FRIENDS, List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isCancel)
                                .action((baseAbilityBot, update) -> sendKeyboard(update, baseAbilityBot, Buttons.MENU_CHALLENGE_YOUR_FRIENDS))
                                .next(mainMenuFlow)
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update1 -> !TelegramUtils.isCancel(update1))
                                .action((baseAbilityBot, update) -> {
                                    challenge.setDifficulty(switchDifficulty(update));
                                    //todo change
                                    sendKeyboard(update, baseAbilityBot, Buttons.MAIN_MENU);
                                }).next(mainMenuFlow)
                                .build()
                )
        );

    }

    private Difficulty switchDifficulty(Update update) {
        //no, it was impossible to do with switch. switch doesn't accept enums...
        String message = update.getMessage().getText();
        if (message.equals(PublicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Difficulty.EASY;
        if (message.equals(PublicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Difficulty.MEDIUM;
        if (message.equals(PublicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Difficulty.DIFFICULT;
        if (message.equals(PublicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Difficulty.GOAL;
        //todo this shouldn't be, but I don't see the other way yet
        return Difficulty.MEDIUM;
    }
}
