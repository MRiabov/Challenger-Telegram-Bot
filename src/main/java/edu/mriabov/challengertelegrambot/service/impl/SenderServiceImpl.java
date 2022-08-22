package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final TelegramBot telegramBot;

    @Override
    public void sendMessages(long chatID, String message) {
        telegramBot.silent().send(message, chatID);
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup) {
        telegramBot.sender().execute(SendMessage.builder()
                .chatId(chatID)
                .text(message)
                .replyMarkup(markup).build());
    }

    @Override
    public void sendMessages(long chatID, Buttons buttons) {

    }

}
