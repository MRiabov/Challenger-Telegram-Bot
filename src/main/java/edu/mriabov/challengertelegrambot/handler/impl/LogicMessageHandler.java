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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogicMessageHandler {

    private final UserRepository userRepository;
    private static final Cache<Long, Challenge> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public Optional<Buttons> handleStaticMessages(Update update) {
        String message = update.getMessage().getText();
        //Nope, can't do enums with switch...
        if (message.equals(LogicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(update.getMessage().getChatId(), Difficulty.EASY));
        if (message.equals(LogicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(update.getMessage().getChatId(), Difficulty.MEDIUM));
        if (message.equals(LogicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(update.getMessage().getChatId(), Difficulty.DIFFICULT));
        if (message.equals(LogicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(update.getMessage().getChatId(), Difficulty.GOAL));

        if (message.equals(LogicButtonsMessages.FINANCES_AREA.getText()))
            return Optional.ofNullable(setArea(update.getMessage().getChatId(), Area.FINANCES));
        if (message.equals(LogicButtonsMessages.FITNESS_AREA.getText()))
            return Optional.ofNullable(setArea(update.getMessage().getChatId(), Area.FITNESS));
        if (message.equals(LogicButtonsMessages.MINDFULNESS_AREA.getText()))
            return Optional.ofNullable(setArea(update.getMessage().getChatId(), Area.MINDFULNESS));
        if (message.equals(LogicButtonsMessages.RELATIONSHIPS_AREA.getText()))
            return Optional.ofNullable(setArea(update.getMessage().getChatId(), Area.RELATIONSHIPS));

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
