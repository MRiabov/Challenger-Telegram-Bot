package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUtils {

    public static final String COMMAND_PREFIX="/";
    private TelegramUtils(){};

    public static boolean isCancel(Update update){
        return update.getMessage().getText().equals(Buttons.cancelMessage);
    }
}
