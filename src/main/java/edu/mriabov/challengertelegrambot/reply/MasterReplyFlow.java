package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.PublicButtonsMessages;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ButtonsUtils.buildMessageWithKeyboard;
import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.*;


@Component
@RequiredArgsConstructor
public class MasterReplyFlow {

    private final ReplyBuilderService replyBuilderService;

    //todo constants instead of methods and a constructor
    public ReplyFlow welcome() {
        return ReplyFlow.builder(TelegramBot.database, 1)
                .next(replyBuilderService.buildSimpleFlow(2, ReceivedMessages.ON_START_NEW_USER_NO, mainMenu()))
                .next(replyBuilderService.buildSimpleFlow(3, ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                replyBuilderService.buildSimpleFlow(4, ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenu()),
                                replyBuilderService.buildSimpleFlow(5, ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                        replyBuilderService.buildSimpleFlow(6, ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenu())
                                ))
                        )
                )
                )
                .build();
    }

    public ReplyFlow mainMenu() {
        return ReplyFlow.builder(TelegramBot.database, 100)
                .action((baseAbilityBot, update) ->
                        baseAbilityBot.silent().execute(buildMessageWithKeyboard(
                                update.getMessage().getChatId(), Buttons.MAIN_MENU)))
                .next(selectDifficulty())
                .next(myChallengesFlow())
                .build();
    }

    //MainMenuFlow
    private ReplyFlow myChallengesFlow() {
        return replyBuilderService.buildSimpleFlow(210, ReceivedMessages.MENU_MY_CHALLENGES, List.of(
                        markAsCompleted(),
                        setGoal(),
                        skipChallenge()
                )
        );
    }

    private ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(220, ReceivedMessages.SET_GOAL,
                List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isCancel)
                                .action((baseAbilityBot, update) -> baseAbilityBot.silent().execute(buildMessageWithKeyboard(update.getMessage().getChatId(), Buttons.SET_GOAL)))
                                .next(myChallengesFlow())
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> !TelegramUtils.isCancel(update))
                                .action(((baseAbilityBot, update) -> {
                                    //todo db operation+if have enough balance
                                    baseAbilityBot.silent().execute(buildMessageWithKeyboard(update.getMessage().getChatId(), Buttons.SET_GOAL));
                                }))
                                .next(myChallengesFlow())
                                .build())
        );
    }

    private ReplyFlow skipChallenge() {
        return replyBuilderService.buildSimpleFlow(230, ReceivedMessages.SKIP_CHALLENGES, List.of(
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isCancel)
                        .action((baseAbilityBot, update) -> buttonsShortcut(update, baseAbilityBot, Buttons.SKIP_CHALLENGES))
                        .next(myChallengesFlow())
                        .build(),
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(update -> !TelegramUtils.isCancel(update))
                        .action((baseAbilityBot, update) -> {
                            //todo dbOperation
                            buttonsShortcut(update, baseAbilityBot, Buttons.SKIP_CHALLENGES);
                        })
                        .next(myChallengesFlow())
                        .build()
        ));
    }

    private ReplyFlow markAsCompleted() {
        return replyBuilderService.buildSimpleFlow(240, ReceivedMessages.MARK_CHALLENGE_AS_COMPLETED,
                List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> update.getMessage().getText().equals(Buttons.cancelMessage))
                                .action((baseAbilityBot, update) -> baseAbilityBot.silent().execute(
                                        buildMessageWithKeyboard(update.getMessage().getChatId(),
                                                Buttons.MARK_CHALLENGE_AS_COMPLETED)))
                                .next(myChallengesFlow())
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update -> !update.getMessage().getText().equals(Buttons.cancelMessage))
                                .action((baseAbilityBot, update) -> {
                                    //todo save!!!
                                    baseAbilityBot.silent().send("Success!", update.getMessage().getChatId());
                                })
                                .next(myChallengesFlow())
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
                                .action((baseAbilityBot, update) -> buttonsShortcut(update, baseAbilityBot, Buttons.MENU_CHALLENGE_YOUR_FRIENDS))
                                .next(mainMenu())
                                .build(),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(update1 -> !TelegramUtils.isCancel(update1))
                                .action((baseAbilityBot, update) -> {
                                    challenge.setDifficulty(switchDifficulty(update));
                                    //todo change
                                    buttonsShortcut(update, baseAbilityBot, Buttons.MAIN_MENU);
                                }).build()
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
