package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.utils.cache.ChallengeCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCreatorServiceImpl implements ChallengeCreatorService {

    private final UserService userService;
    private final ChatService chatService;
    private final ChallengeCache challengeCache;

    @Override
    public boolean selectUsers(long chatID, int selectedNumber) {
        if (deletedFromCache(chatID)) return false;
        challengeCache.get(chatID).setChatID(userService.selectByNumber(chatID, selectedNumber));
        return true;
    }

    @Override
    public void selectChats(long chatID, int selectedNumber) {
        Challenge challenge = new Challenge();
        challenge.setChatID(chatService.selectOnPageByNumber(chatID, selectedNumber));
        challengeCache.put(chatID, challenge);
    }

    @Override
    public boolean selectDifficulty(long chatID, Difficulty difficulty) {
        if (deletedFromCache(chatID)) return false;
        challengeCache.get(chatID).setDifficulty(difficulty);
        return true;
    }

    @Override
    public boolean selectArea(long chatID, Area area) {
        if (deletedFromCache(chatID)) return false;
        challengeCache.get(chatID).setArea(area);
        return true;
    }

    @Override
    public boolean selectUsersByUsername(long chatID, String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) return false;
        //todo check whether this user is in a chat.
        //todo cache must be checked (???)
        challengeCache.get(chatID).getUsers().add(userOptional.get());
        return true;
    }

    private boolean deletedFromCache(long chatID) {
        return !challengeCache.contains(chatID);
    }
}
