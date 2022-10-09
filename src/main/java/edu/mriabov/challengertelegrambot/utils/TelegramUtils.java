package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelegramUtils {

    private TelegramUtils() {
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static boolean checkForUnsupportedEmoji(String message) {
        String startOfMessage = message.substring(0, 3);
        List<String> emojis = List.of("\uD83D\uDFE2", "🟣", "⃣", "\uD83E\uDEF6");
        for (String emoji : emojis) if (startOfMessage.contains(emoji)) return true;
        return false;
    }

    public static String linkBuilder(long chatID) {
        return "https://t.me/BecomeGigachad_Bot?start=" + chatID;
    }

    public static int getOffset(String message) {
        int offset;
        int spaceCount = 0;
        for (offset = 0; spaceCount != 3; offset++) {
            if (message.charAt(offset) == ' ') spaceCount++;
        }
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
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setExpiresAt(LocalDateTime.now().plusHours(24));

        for (String arg : arguments) parametersForChallenge(arg, challenge);
        return challenge;
    }

    private static void parametersForChallenge(String word, Challenge challenge) {
        Difficulty difficulty = null;
        Area area = null;
        try {
            difficulty = Difficulty.valueOf(word.toUpperCase());
            area = Area.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException ignored) {}
        if (challenge.getDifficulty() == null && difficulty != null) {
            challenge.setDifficulty(difficulty);
        }

        if (challenge.getArea() == null && area != null) {
            challenge.setDifficulty(difficulty);
        }
    }
}
