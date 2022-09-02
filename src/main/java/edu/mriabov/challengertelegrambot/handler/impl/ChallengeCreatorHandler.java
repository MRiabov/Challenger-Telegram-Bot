package edu.mriabov.challengertelegrambot.handler.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChatRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChallengeCreatorHandler {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final SenderService senderService;

    private static final Cache<Long, Challenge> challengeCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();
    private static final Cache<Long, Integer> pageNumCache = CacheBuilder
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

        if (message.startsWith("️⃣", 1)) return Optional.of(dynamicButtonsHandler(message,
                chatID, Integer.parseInt(String.valueOf(message.charAt(0)))));

        //handle message here. then it goes to

        return Optional.empty();
    }

    public Buttons handleUsernames(Update update) {
        Optional<User> userOptional = userRepository.getUserByUsername(update.getMessage().getText().substring(1));
        if (userOptional.isEmpty()) return Buttons.OTHER_USER_NOT_FOUND;
        //todo check whether this user is in a chat.
        //todo cache must be checked (???)
        challengeCache.asMap().get(update.getMessage().getChatId()).getUsers().add(userOptional.get());
        return Buttons.DIFFICULTY_SELECTION;
    }

    private Buttons dynamicButtonsHandler(String message, long chatID, int selectedNumber) {
        if (message.substring(4).equals(Appendix.USER_APPENDIX.getText()))
            return selectChats(chatID, selectedNumber);
        //todo same, but with username
        if (message.substring(4).equals(Appendix.CHAT_APPENDIX.getText()))
            return selectUsers(chatID, selectedNumber);
        return senderService.breakAttempt(chatID);
    }

    private Buttons selectChats(long chatID, int selectedNumber) {
        if (userRepository.getUserByTelegramId(chatID).isEmpty()) return senderService.userDoesNotExist(chatID);
        if (selectedNumber > userRepository.countChatsById(chatID)) return senderService.breakAttempt(chatID);
        Challenge challenge = new Challenge();
        challenge.setChatID(userRepository.findAllByTelegramId(
                        chatID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                                .withPage(pageNumCache.asMap().getOrDefault(chatID, 0)))
                .getContent().get(selectedNumber).getTelegramID());
        challengeCache.asMap().put(chatID, challenge);
        return Buttons.USER_SELECTION;
    }

    private Buttons selectUsers(long chatID, int selectedNumber) {
        if (!challengeCache.asMap().containsKey(chatID)) return senderService.deletedFromCache(chatID);
        if (userRepository.getUserByTelegramId(chatID).isEmpty()) return senderService.userDoesNotExist(chatID);
        if (selectedNumber > userRepository.countChatsById(chatID)) return senderService.breakAttempt(chatID);
        challengeCache.asMap().get(chatID).setChatID(
                chatRepository.findAllByTelegramID(chatID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                                .withPage(pageNumCache.asMap().getOrDefault(chatID, 0)))
                        .getContent().get(selectedNumber).getTelegramId());
        return Buttons.DIFFICULTY_SELECTION;
    }

    private Buttons setDifficulty(long chatID, Difficulty difficulty) {
        if (!challengeCache.asMap().containsKey(chatID)) return senderService.deletedFromCache(chatID);
        challengeCache.asMap().putIfAbsent(chatID, new Challenge());
        Challenge challenge = challengeCache.asMap().get(chatID);
        challenge.setDifficulty(difficulty);
        challengeCache.asMap().put(chatID, challenge);
        return Buttons.AREA_SELECTION;
    }

    private Buttons setArea(long chatID, Area area) {
        if (!challengeCache.asMap().containsKey(chatID)) return senderService.deletedFromCache(chatID);
        //check if the object wasn't deleted from cache by waiting out a timer.
        Challenge challenge = challengeCache.asMap().get(chatID);
        challenge.setArea(area);
        challengeCache.asMap().put(chatID, challenge);
        return Buttons.AREA_SELECTION;
    }
}
