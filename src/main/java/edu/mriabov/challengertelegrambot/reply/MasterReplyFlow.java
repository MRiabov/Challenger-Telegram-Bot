package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ButtonsUtils.buildMessageWithKeyboard;
import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.*;


@Component
@RequiredArgsConstructor
public class MasterReplyFlow {

    private final ReplyBuilderService replyBuilderService;

    //todo constants instead of methods and a constructor
    public ReplyFlow welcomeFlow() {
        return ReplyFlow.builder(TelegramBot.database)
                .next(replyBuilderService.buildSimpleFlow(ReceivedMessages.ON_START_NEW_USER_NO, mainMenuFlow()))
                .next(replyBuilderService.buildSimpleFlow(ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                replyBuilderService.buildSimpleFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenuFlow()),
                                replyBuilderService.buildSimpleFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                        replyBuilderService.buildSimpleFlow(ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenuFlow())
                                ))
                        )
                ))
                .build();
    }

    public ReplyFlow mainMenuFlow() {
        return ReplyFlow.builder(TelegramBot.database)
                .action((baseAbilityBot, update) ->
                        baseAbilityBot.silent().execute(buildMessageWithKeyboard(
                                update.getMessage().getChatId(), Buttons.MAIN_MENU)))
                .next(challengeCreateFlow())
                .next(myChallengesFlow())
                .build();
    }

    private ReplyFlow myChallengesFlow() {
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.MENU_MY_CHALLENGES, List.of(
                        markAsCompleted(),
                        setGoal(),
                        skipChallenge()
                )
        );
    }

    private ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.SET_GOAL,
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
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.SKIP_CHALLENGES, List.of(
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
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.MARK_CHALLENGE_AS_COMPLETED,
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


    private ReplyFlow challengeCreateFlow() {
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.MENU_CHALLENGE_YOUR_FRIENDS, List.of(
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isCancel)
                                .action((baseAbilityBot, update) -> buttonsShortcut(update,baseAbilityBot,Bu))
                                .action(())
                                .build()
                )
        );
    }
}
