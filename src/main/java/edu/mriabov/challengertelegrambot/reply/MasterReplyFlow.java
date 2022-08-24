package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
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


@Component
@RequiredArgsConstructor
public class MasterReplyFlow {

    private final ReplyBuilderService replyBuilderService;

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

    //how do free replies work?
    //if (updateMsg!=buttons.cancel save it. wherever it is, I don't know. if cancel set to masterReplyFlow.)
    private ReplyFlow myChallengesFlow() {
        return replyBuilderService.buildSimpleFlow(ReceivedMessages.MENU_MY_CHALLENGES, List.of(
                        markAsCompleted(),
                        setGoal(),
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
                                .action(((baseAbilityBot, update) -> baseAbilityBot.silent().execute(buildMessageWithKeyboard(update.getMessage().getChatId(), Buttons.SET_GOAL))))
                                .next()
                                .build())
        );
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
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().getText().equals(Buttons.CHALLENGE_YOUR_FRIENDS.getMessage()))
                .build();
    }
}
