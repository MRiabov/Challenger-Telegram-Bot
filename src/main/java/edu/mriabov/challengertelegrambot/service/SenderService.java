package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface SenderService {

    void sendMessages(long userID, String message);

    void sendMessages(long userID, String message, ReplyKeyboardMarkup markup);

    void sendMessages(long userID, Buttons buttons);

    void sendMessages(SendMessage sendMessage);

    void replyToMessage(Message msgToReply,String message);


}
