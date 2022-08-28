package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ButtonsUtils {
    private ButtonsUtils(){}
    public static SendMessage buildMessageWithKeyboard(long chatID, Buttons buttons){
        SendMessage sendMessage = new SendMessage(Long.toString(chatID),buttons.getMessage());
        sendMessage.setReplyMarkup(arrayToReplyMarkup(buttons.getKeyboard()));
        return sendMessage;
    }

    private static ReplyKeyboardMarkup arrayToReplyMarkup(String[] buttons) {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (String button : buttons) {
            if (isNull(button)) {
                rowList.add(currentRow);
                currentRow = new KeyboardRow();
            } else currentRow.add(button);
        }
        return new ReplyKeyboardMarkup(rowList);
    }



}
