package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ReplyUtils {

    public static ReplyFlow buildFlow(ReceivedMessages receivedMessages, TelegramBot telegramBot) {
        return ReplyFlow.builder(telegramBot.db())
                .onlyIf(update -> update.getMessage().getText().equals(receivedMessages.getText()))
                .action((baseAbilityBot, update) -> {
                    try {
                        telegramBot.execute(ButtonsUtils.buildSendMessageWithKeyboard(
                                update.getMessage().getChatId(),receivedMessages.getNextInvocation()
                        ));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }



}
