package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MessageSenderService {

    void sendMessage(long chatID,String text);
    void sendMessage(long chatID,String text, ReplyKeyboardMarkup markup);

}
