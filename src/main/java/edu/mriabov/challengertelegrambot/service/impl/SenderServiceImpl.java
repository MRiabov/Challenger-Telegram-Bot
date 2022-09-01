package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.FormatService;
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

    private final FormatService formatService;
    private final BotConfig botConfig;

    @Autowired
    protected SenderServiceImpl(FormatService formatService, BotConfig botConfig) {
        super(new DefaultBotOptions());
        this.formatService = formatService;
        this.botConfig = botConfig;
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message) {
        message = formatService.format(chatID, message);
        log.info("SenderService attempted to send a message: " + message);
        execute(new SendMessage(Long.toString(chatID), message));

    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup) {
        message = formatService.format(chatID, message);
        log.info("SenderService attempted to send a message with markup: " + message);
        execute(SendMessage.builder()
                .text(message)
                .chatId(chatID)
                .replyMarkup(markup)
                .build());
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, Buttons buttons) {
        String message = formatService.format(chatID, buttons.getMessage());
        log.info("SenderService attempted to send a message with buttons: " + buttons.getMessage());
        execute(SendMessage.builder()
                .text(message)
                .chatId(chatID)
                .replyMarkup(ButtonsUtils.createStaticMarkup(buttons.getKeyboard()))
                .build()
        );
    }

    @Override
    public Buttons userDoesNotExist(long chatID) {
        sendMessages(chatID,
                "Error: User does not exist! Please use /start.",
                ButtonsUtils.createStaticMarkup(Buttons.MAIN_MENU.getKeyboard()));
        return Buttons.MAIN_MENU;//because method always marks failure
    }

    @Override
    public Buttons breakAttempt(long chatID) {
        sendMessages(chatID, """
                Incorrect input! The system is foolproof! (for everyone except the programmer, at least)
                Just click the buttons, please.
                """);
        return Buttons.MAIN_MENU;//because method always marks failure
    }

    @Override
    public Buttons deletedFromCache(long chatID) {
        sendMessages(chatID, """
                It seems that you have waited for too long, and your challenge has deleted itself(30 minutes).
                Don't worry, you can just scroll up and get it from the messages you've sent before.
                """);
        return Buttons.USER_SELECTION;//because method always starts the challenge creation
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
