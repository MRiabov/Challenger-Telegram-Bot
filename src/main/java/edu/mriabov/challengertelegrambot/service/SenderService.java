package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface SenderService {

    void sendMessages(long chatID,String message);
    void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup);
    void sendMessages(long chatID, Buttons buttons);

}
