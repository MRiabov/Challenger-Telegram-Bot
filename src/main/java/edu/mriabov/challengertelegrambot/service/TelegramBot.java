package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.MasterMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

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


            message.setReplyMarkup(new ReplyKeyboardMarkup(
                    new ArrayList<>(List.of(
                            new KeyboardRow(List.of(new KeyboardButton("My challenges"))),
                            new KeyboardRow(List.of(new KeyboardButton("Challenge your friends"))),
                            new KeyboardRow(List.of(new KeyboardButton("Buy some rest..."),
                                    new KeyboardButton("FAQ")))))));
        }
    }

}
