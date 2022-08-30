package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicButtonServiceImpl implements DynamicButtonsService {


    @Override
    public ReplyKeyboardMarkup createMarkup(String appendix, @Max(value = 9) int pageSize) {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (int i = 1; i <= pageSize; i++) {
            currentRow.add(new KeyboardButton(switchKeyboard(i) + " " + appendix));
            if (i % 3 == 0) {
                rowList.add(currentRow);
                currentRow = new KeyboardRow();
            }
        }
        if (!currentRow.isEmpty()) rowList.add(currentRow);
        rowList.add(finalRow(appendix));
        return new ReplyKeyboardMarkup(rowList);
    }

    private KeyboardRow finalRow(String appendix){
        return new KeyboardRow(List.of(
                new KeyboardButton("⏪ "+appendix + " page"),
                new KeyboardButton(ReceivedMessages.CANCEL.getReceivedMessage()),
                new KeyboardButton("⏩ "+appendix + " page")
        ));
    }

    private String switchKeyboard(int i) {
        switch (i) {
            case 1 -> {
                return "1️⃣";
            }
            case 2 -> {
                return "2️⃣";
            }
            case 3 -> {
                return "3️⃣";
            }
            case 4 -> {
                return "4️⃣";
            }
            case 5 -> {
                return "5️⃣";
            }
            case 6 -> {
                return "6️⃣";
            }
            case 7 -> {
                return "7️⃣";
            }
            case 8 -> {
                return "8️⃣";
            }
            case 9 -> {
                return "9️⃣";
            }
            default -> throw new IllegalStateException("Unexpected value: " + i);
        }

    }
}
