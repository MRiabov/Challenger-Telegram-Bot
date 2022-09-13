package edu.mriabov.challengertelegrambot.privatechat.utils;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelegramUtils {

    private TelegramUtils() {
    }

    public static boolean checkForUnsupportedEmoji(String message) {
        String startOfMessage = message.substring(0, 3);
        List<String> emojis = List.of("\uD83D\uDFE2", "🟣", "⃣");
        for (String emoji : emojis) if (startOfMessage.contains(emoji)) return true;
        return false;
    }

    public static String linkBuilder(long groupID) {
        return "https://t.me/BecomeGigachad_Bot?start=" + groupID;
    }

    public static int getOffset(String message) {
        int offset;
        int spaceCount = 0;
        for (offset = 0; spaceCount!=3; offset++) if (message.charAt(offset) == ' ') spaceCount++;
        return offset;
    }

    public static boolean isUserAdmin(AbsSender absSender, Message message) throws TelegramApiException {
        for (ChatMember admin : absSender.execute(GetChatAdministrators.builder().chatId(message.getChatId()).build())) {
            if (admin.getUser().getId().equals(message.getFrom().getId())) return true;
        }
        return false;
    }

    public static Challenge challengeBasicInfo(String[] arguments) {
        Challenge challenge = new Challenge();
        for (String arg : arguments) parametersForChallenge(arg, challenge);
        return challenge;
    }


    private static void parametersForChallenge(String word, Challenge challenge) {
        switch (word.toLowerCase()) {
            case "easy" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.EASY);
            }
            case "medium" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.MEDIUM);
            }
            case "difficult" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.DIFFICULT);
            }
            case "goal" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.GOAL);
            }
            case "fitness" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.FITNESS);
            }
            case "relationships" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.RELATIONSHIPS);
            }
            case "finances" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.FINANCES);
            }
            case "mindfulness" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.MINDFULNESS);
            }
        }
    }
}
