package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.MasterMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    final MasterMessageHandler messageHandler;

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText();
        }

    }

}
