package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.ReceivedMessages;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ButtonsMappingUtils {
    private ButtonsMappingUtils() {
    }

    public static final String NEXT_PAGE = "⏩📄 ";
    public static final String PREVIOUS_PAGE = "⏪📄 ";

    public static SendMessage buildMessageWithKeyboard(long chatID, Buttons buttons) {
        SendMessage sendMessage = new SendMessage(Long.toString(chatID), buttons.getMessage());
        sendMessage.setReplyMarkup(createStaticMarkup(buttons.getKeyboard()));
        return sendMessage;
    }

    public static ReplyKeyboardMarkup createStaticMarkup(String[] buttons) {
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

    public static final int PAGE_SIZE = 9;

    public static ReplyKeyboardMarkup createDynamicMarkup(String appendix, @Max(value = PAGE_SIZE) int pageSize) {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (int i = 1; i <= pageSize; i++) {
            currentRow.add(new KeyboardButton(symbol(i) + " " + appendix));
            if (i % 3 == 0) {
                rowList.add(currentRow);
                currentRow = new KeyboardRow();
            }
        }
        if (!currentRow.isEmpty()) rowList.add(currentRow);
        rowList.add(finalRow(appendix));
        return new ReplyKeyboardMarkup(rowList);
    }

    private static KeyboardRow finalRow(String appendix) {
        return new KeyboardRow(List.of(
                new KeyboardButton(PREVIOUS_PAGE + appendix),
                new KeyboardButton(ReceivedMessages.CANCEL.getReceivedMessage()),
                new KeyboardButton(NEXT_PAGE + appendix)
        ));
    }

    private static String symbol(int i) {
        if (i > PAGE_SIZE)
            throw new IllegalStateException("Unexpected value: " + i + ". Value must be below " + PAGE_SIZE);
        return i + "️⃣"; //returns "1️⃣" or a similar emoji
    }

}
