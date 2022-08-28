package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@Slf4j
public class SenderServiceImpl extends DefaultAbsSender implements SenderService {

    BotConfig botConfig;

    @Autowired
    protected SenderServiceImpl(BotConfig botConfig) {
        super(new DefaultBotOptions());
        this.botConfig = botConfig;
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message) {
        log.info("SenderService attempted to send a message: "+message);
        execute(new SendMessage(Long.toString(chatID), message));

    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup) {
        log.info("SenderService attempted to send a message with markup: "+message);
        execute(SendMessage.builder()
                .text(message)
                .chatId(chatID)
                .replyMarkup(markup)
                .build());
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, Buttons buttons) {
        log.info("SenderService attempted to send a message with buttons: "+buttons.getMessage());
        execute(ButtonsUtils.buildMessageWithKeyboard(chatID, buttons));

    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
