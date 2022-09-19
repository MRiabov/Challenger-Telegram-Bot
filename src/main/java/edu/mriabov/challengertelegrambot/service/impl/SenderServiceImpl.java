package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@Slf4j
public class SenderServiceImpl extends DefaultAbsSender implements SenderService {

    private final FormatService formatService;
    private final BotConfig botConfig;
    private final DynamicButtonsService dynamicButtonsService;

    @Autowired
    protected SenderServiceImpl(FormatService formatService, BotConfig botConfig, DynamicButtonsService dynamicButtonsService) {
        super(new DefaultBotOptions());
        this.formatService = formatService;
        this.botConfig = botConfig;
        this.dynamicButtonsService = dynamicButtonsService;
    }

    @SneakyThrows
    @Override
    public void sendMessages(long userID, String message) {
        message = formatService.format(userID, message);
        log.info("SenderService attempted to send a message: " + message);
        execute(new SendMessage(Long.toString(userID), message));
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
//                .parseMode("HTML")
                .build());
    }

    @SneakyThrows
    @Override
    public void sendMessages(long userID, Buttons buttons) {
        String message = formatService.format(userID, buttons.getMessage());
        log.info("SenderService attempted to send a message with buttons: " + buttons.getMessage());

        execute(SendMessage.builder()
                        .text(message)
                        .chatId(userID)
                        .replyMarkup(buttons.getKeyboard().length == 1 ?
                                dynamicButtonsService.createMarkup(userID, buttons.getKeyboard()[0]) :
                                ButtonsMappingUtils.createStaticMarkup(buttons.getKeyboard()))
//                .parseMode("HTML")
                        .build()
        );
    }

    @SneakyThrows
    @Override
    public void sendMessages(SendMessage sendMessage) {
        log.info("SenderService attempted to send a SendMessage: " + sendMessage.toString());
        sendMessage.setText(formatService.format(Long.parseLong(sendMessage.getChatId()), sendMessage.getText()));
//        sendMessage.setParseMode("HTML");
        execute(sendMessage);
    }

    @SneakyThrows
    @Override
    public void replyToMessage(Message msgToReply, String message) {
        message = formatService.format(msgToReply.getFrom().getId(), message);//this one
        execute(SendMessage.builder()
                .replyToMessageId(msgToReply.getMessageId())
                .text(message)
                .chatId(msgToReply.getChatId())
//                .parseMode("HTML")
                .build());
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}