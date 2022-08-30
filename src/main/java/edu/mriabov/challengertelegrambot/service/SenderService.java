package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
@Service
public interface SenderService {

    void sendMessages(long chatID,String message);
    void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup);
    void sendMessages(long chatID, Buttons buttons);
    void userDoesNotExist(long chatID);



}
