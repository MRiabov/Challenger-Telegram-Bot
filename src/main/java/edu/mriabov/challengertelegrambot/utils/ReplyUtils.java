package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class ReplyUtils {
//todo remove tg bot
    public static ReplyFlow buildFlow(ReceivedMessages receivedMessages,List<ReplyFlow> next, TelegramBot telegramBot) {
        ReplyFlow.ReplyFlowBuilder builder = ReplyFlow.builder(telegramBot.db());
        builder.onlyIf(update -> update.getMessage().getText().equals(receivedMessages.getReceivedMessage()));
        builder.action((baseAbilityBot, update) -> execute(receivedMessages, telegramBot, update));
        for (ReplyFlow nextReply:next) builder.next(nextReply);
        return builder.build();
    }



    private static void execute(ReceivedMessages receivedMessages, TelegramBot telegramBot, Update update) {
        try {
            telegramBot.execute(ButtonsUtils.buildSendMessageWithKeyboard(
                    update.getMessage().getChatId(), receivedMessages.getNextInvocation()
            ));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
