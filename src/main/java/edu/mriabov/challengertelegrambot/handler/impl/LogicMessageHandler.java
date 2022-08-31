package edu.mriabov.challengertelegrambot.handler.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogicMessageHandler {

    private final UserRepository userRepository;
    private final SenderService senderService;
    private static final Cache<Long, Challenge> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public Optional<Buttons> handleStaticMessages(Update update) {
        String message = update.getMessage().getText();
        long chatID = update.getMessage().getChatId();
        //Nope, can't do enums with switch...
        if (message.equals(LogicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.EASY));
        if (message.equals(LogicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.MEDIUM));
        if (message.equals(LogicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.DIFFICULT));
        if (message.equals(LogicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.GOAL));

        if (message.equals(LogicButtonsMessages.FINANCES_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.FINANCES));
        if (message.equals(LogicButtonsMessages.FITNESS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.FITNESS));
        if (message.equals(LogicButtonsMessages.MINDFULNESS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.MINDFULNESS));
        if (message.equals(LogicButtonsMessages.RELATIONSHIPS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.RELATIONSHIPS));

        Buttons pagedButtons = switch (message.substring(0, 3)) {
            case "1️⃣" -> dynamicButtonsHandler(message, chatID, 1);
            case "2️⃣" -> dynamicButtonsHandler(message, chatID, 2);
            case "3️⃣" -> dynamicButtonsHandler(message, chatID, 3);
            case "4️⃣" -> dynamicButtonsHandler(message, chatID, 4);
            case "5️⃣" -> dynamicButtonsHandler(message, chatID, 5);
            case "6️⃣" -> dynamicButtonsHandler(message, chatID, 6);
            case "7️⃣" -> dynamicButtonsHandler(message, chatID, 7);
            case "8️⃣" -> dynamicButtonsHandler(message, chatID, 8);
            case "9️⃣" -> dynamicButtonsHandler(message, chatID, 9);
            default -> null;
        };
        if (pagedButtons != null) {
            return Optional.of(pagedButtons);
        }

        //handle message here. then it goes to

        return Optional.empty();
    }

    public Buttons handleUsernames(Update update) {
        Optional<User> userOptional = userRepository.getUserByUsername(update.getMessage().getText().substring(1));
        if (userOptional.isPresent()) {//todo cache must be checked
            cache.asMap().get(update.getMessage().getChatId()).getUsers().add(userOptional.get());
            return Buttons.DIFFICULTY_SELECTION;
        } else return Buttons.OTHER_USER_NOT_FOUND;
    }

    private Buttons dynamicButtonsHandler(String message, long chatID, int selectedNumber) {
        if (message.substring(4).equals(LogicButtonsMessages.USER_APPENDIX.getText())) {
            Optional<User> userOptional = userRepository.getUserByTelegramId(chatID);
            if (userOptional.isPresent()) {
                if (userOptional.get().getChatList().size() < selectedNumber) {
                    cache.asMap().get(chatID).setChatID(userOptional.get().getChatList().get(selectedNumber).getTelegramChatID());
                    return Buttons.USER_SELECTION;
                } else return Buttons.MAIN_MENU;
            } else {
                senderService.userDoesNotExist(chatID);
                return Buttons.MAIN_MENU;
            }
        }//todo same, but with username

        senderService.sendMessages(chatID,"""
        Incorrect input! The system is foolproof! (for everyone except the programmer, at least)
        Just click the buttons, please.
        """);

        return Buttons.MAIN_MENU;
    }

    private Buttons setDifficulty(long chatID, Difficulty difficulty) {
        cache.asMap().putIfAbsent(chatID, new Challenge());
        Challenge challenge = cache.asMap().get(chatID);
        challenge.setDifficulty(difficulty);
        cache.asMap().put(chatID, challenge);
        return Buttons.AREA_SELECTION;
    }

    private Buttons setArea(long chatID, Area area) {
        if (!cache.asMap().containsKey(chatID)) return Buttons.USER_SELECTION;
        //check if the object wasn't deleted from cache by waiting out a timer.
        Challenge challenge = cache.asMap().get(chatID);
        challenge.setArea(area);
        cache.asMap().put(chatID, challenge);
        return Buttons.AREA_SELECTION;
    }
}
