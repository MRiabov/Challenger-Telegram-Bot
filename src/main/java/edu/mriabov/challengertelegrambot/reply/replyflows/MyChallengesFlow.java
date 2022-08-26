package edu.mriabov.challengertelegrambot.reply.replyflows;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.sendMenu;

@Service
@RequiredArgsConstructor
public class MyChallengesFlow implements AbilityExtension {
    private final ReplyBuilderService replyBuilderService;

    public ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(220, ReceivedMessages.SET_GOAL,
                List.of(
                        replyBuilderService.cancelMessage(Buttons.MENU_MY_CHALLENGES),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isNotCancel)
                                .action(((baseAbilityBot, update) -> {
                                    //todo db operation+if have enough balance
                                    sendMenu(update, baseAbilityBot, Buttons.MENU_MY_CHALLENGES);
                                }))//todo time, area and whatever selection.
                                .build())
        );
    }

    public ReplyFlow skipChallenge() {
        return replyBuilderService.buildSimpleFlow(230, ReceivedMessages.SKIP_CHALLENGES, List.of(
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isCancel)
                        .action((baseAbilityBot, update) -> sendMenu(update, baseAbilityBot, Buttons.SKIP_CHALLENGES))
                        .build(),
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isNotCancel)
                        .action((baseAbilityBot, update) -> {
                            //todo dbOperation
                            sendMenu(update, baseAbilityBot, Buttons.SKIP_CHALLENGES);
                        })
                        .build()
        ));
    }

    public ReplyFlow markAsCompleted() {
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
}
