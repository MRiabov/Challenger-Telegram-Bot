package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
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
                .replyMarkup(buttons.getKeyboard().length==0 ?// FIXME: 9/3/2022 chat appendix is hardcoded
                        dynamicButtonsService.createMarkup(chatID,Appendix.CHAT_APPENDIX) :
                        ButtonsMappingUtils.createStaticMarkup(buttons.getKeyboard()))
                .build()
        );
    }
    @SneakyThrows
    @Override
    public void sendMessages(SendMessage sendMessage) {
        sendMessage.setText(formatService.format(Long.parseLong(sendMessage.getChatId()), sendMessage.getText()));
        execute(sendMessage);
    }

    @SneakyThrows
    @Override
    public void replyToMessage(Message msgToReply, String message) {
        message = formatService.format(msgToReply.getChatId(), message);
        execute(SendMessage.builder().
                replyToMessageId(msgToReply.getMessageId())
                .text(message)
                .chatId(msgToReply.getChatId())
                .build());
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}