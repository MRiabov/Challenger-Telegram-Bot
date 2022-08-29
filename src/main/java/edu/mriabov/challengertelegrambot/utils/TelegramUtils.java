package edu.mriabov.challengertelegrambot.utils;

import java.util.List;

public class TelegramUtils {
    private TelegramUtils(){};

    public static boolean checkForUnsupportedEmoji(String message){
        String startOfMessage = message.substring(0,3);
        List<String> emojis = List.of("\uD83D\uDFE2","🟣","⃣");
        for (String emoji:emojis) if (startOfMessage.contains(emoji)) return true;
        return false;
    }

}
