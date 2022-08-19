package edu.mriabov.challengertelegrambot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TelegramUtils {

    public static ReplyKeyboardMarkup ArrayToReplyMarkup(String[] buttons) {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (String button : buttons) {
            if (Objects.equals(button, null)) {
                rowList.add(currentRow);
                currentRow = new KeyboardRow();
            } else currentRow.add(button);
        }
        return new ReplyKeyboardMarkup(rowList);
    }

}
