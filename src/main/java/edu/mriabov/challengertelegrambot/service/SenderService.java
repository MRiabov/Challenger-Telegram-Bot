package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface SenderService {

    void sendMessages(long chatID,String message);
    void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup);
    void sendMessages(long chatID, Buttons buttons);
    Buttons userDoesNotExist(long chatID);
    Buttons breakAttempt(long chatID);
    Buttons deletedFromCache(long chatID);


}
