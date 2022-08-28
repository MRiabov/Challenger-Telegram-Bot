package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
@Service

public class SenderServiceImpl extends DefaultAbsSender implements SenderService {

    BotConfig botConfig;

    @Autowired
    protected SenderServiceImpl(BotConfig botConfig) {
        super(new DefaultBotOptions());
        this.botConfig=botConfig;
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message) {
        execute(new SendMessage(Long.toString(chatID), message));
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup) {
        execute(SendMessage.builder()
                        .chatId(chatID)
                .replyMarkup(markup)
                .build());
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, Buttons buttons) {
        execute(ButtonsUtils.buildMessageWithKeyboard(chatID,buttons));
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
