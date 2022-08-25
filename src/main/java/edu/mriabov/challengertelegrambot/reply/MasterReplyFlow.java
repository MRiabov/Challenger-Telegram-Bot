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
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.sendKeyboard;


@Component
public class MasterReplyFlow {

    //ids are to be labeled: [x]00 for separate menu buttons, 0[x]0 for features in menu, 00[x] for subfeatures.
    //todo constants instead of methods and a constructor

    private final ReplyBuilderService replyBuilderService;
    //    private final ReplyFlow welcomeFlow = welcome();
    //    menu is for a public replies, flow is for partially inaccessible flows.
    private final Reply mainMenu;
    private final Reply myChallengesMenu;
    private final ReplyFlow setGoalFlow;
    private final ReplyFlow selectDifficultyFlow;
    private final ReplyFlow skipChallengeFlow;
    private final ReplyFlow markAsCompletedFlow;

    @Autowired
    public MasterReplyFlow(ReplyBuilderService replyBuilderService) {
        this.replyBuilderService = replyBuilderService;

        mainMenu = replyBuilderService.buildSimpleReply(ReceivedMessages.MAIN_MENU);
        myChallengesMenu = replyBuilderService.buildSimpleReply(ReceivedMessages.MENU_MY_CHALLENGES);
        setGoalFlow = setGoal();
        selectDifficultyFlow = selectDifficulty();
        skipChallengeFlow = skipChallenge();
        markAsCompletedFlow = markAsCompleted();
    }

    public Reply mainMenu() {
        return replyBuilderService.buildSimpleReply(ReceivedMessages.MAIN_MENU);
    }

    public Reply myChallenges() {
        return replyBuilderService.buildSimpleReply(ReceivedMessages.MENU_MY_CHALLENGES);
    }

    public ReplyFlow welcome() {
        return ReplyFlow.builder(TelegramBot.database, 1)
                .next(replyBuilderService.buildSimpleFlow(2, ReceivedMessages.ON_START_NEW_USER_NO, mainMenu))
                .next(replyBuilderService.buildSimpleFlow(3, ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                        replyBuilderService.buildSimpleFlow(4, ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenu),
                                        replyBuilderService.buildSimpleFlow(5, ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                                replyBuilderService.buildSimpleFlow(6, ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenu)
                                        ))
                                )
                        )
                )
                .build();
    }

    //my challenges menu
    private ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(220, ReceivedMessages.SET_GOAL,
                List.of(
                        replyBuilderService.cancelMessage(Buttons.MENU_MY_CHALLENGES),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isNotCancel)
                                .action(((baseAbilityBot, update) -> {
                                    //todo db operation+if have enough balance
                                    sendKeyboard(update, baseAbilityBot, Buttons.MENU_MY_CHALLENGES);
                                }))
                                .next(myChallengesMenu)//todo time, area and whatever selection.
                                .build())
        );
    }

    private ReplyFlow skipChallenge() {
        return replyBuilderService.buildSimpleFlow(230, ReceivedMessages.SKIP_CHALLENGES, List.of(
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isCancel)
                        .action((baseAbilityBot, update) -> sendKeyboard(update, baseAbilityBot, Buttons.SKIP_CHALLENGES))
                        .build(),
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isNotCancel)
                        .action((baseAbilityBot, update) -> {
                            //todo dbOperation
                            sendKeyboard(update, baseAbilityBot, Buttons.SKIP_CHALLENGES);
                        })
                        .build()
        ));
    }

    private ReplyFlow markAsCompleted() {
        return replyBuilderService.buildSimpleFlow(240, ReceivedMessages.MARK_CHALLENGE_AS_COMPLETED,
                List.of(
                        replyBuilderService.cancelMessage(Buttons.MENU_MY_CHALLENGES),
                        Reply.of(
                                ((baseAbilityBot, update) -> {
                                    //todo save!!!
                                    baseAbilityBot.silent().send("Success!", update.getMessage().getChatId());
                                }),
                                TelegramUtils::isNotCancel)
                )
        );
    }

    //ChallengeCreateFlow
    private ReplyFlow selectDifficulty() {
        Challenge challenge = new Challenge();
        return replyBuilderService.buildSimpleFlow(310, ReceivedMessages.MENU_CHALLENGE_YOUR_FRIENDS, List.of(
                        replyBuilderService.cancelMessage(Buttons.MAIN_MENU),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isNotCancel)
                                .action((baseAbilityBot, update) -> {
                                    challenge.setDifficulty(switchDifficulty(update));
                                    //todo change, warn user about incorrect input.
                                    sendKeyboard(update, baseAbilityBot, Buttons.MAIN_MENU);
                                }).next(mainMenu)
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
