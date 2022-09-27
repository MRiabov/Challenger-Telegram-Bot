package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SenderService {

    void sendMessages(long userID, String message);

    void sendMessages(long userID, Buttons buttons);

    void sendMessages(SendMessage sendMessage);

    void replyToMessage(Message msgToReply,String message);


}
