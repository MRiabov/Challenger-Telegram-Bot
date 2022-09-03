package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCreatorServiceImpl implements ChallengeCreatorService {

    private final UserService userService;
    private final ChatService chatService;
    private final ChallengeCache challengeCache;
    private final UserPageCache userPageCache;
    private final ChatPageCache chatPageCache;

    @Override
    public void fillUserPageCache(long chatID) {
        userPageCache.put(chatID,chatService.findAllByTelegramID(chatID,1));
    }

    @Override
    public void fillChatPageCache(long chatID) {
        chatPageCache.put(chatID,
                userService.findAllByTelegramId(chatID,1));
    }

    @Override
    public boolean selectUsers(long chatID, User user) {
        if (deletedFromCache(chatID)) return false;
        challengeCache.get(chatID).getUsers().add(user);
        return true;
    }

    @Override
    public boolean selectChats(long chatID, int selectedNumber) {
        if (!chatPageCache.contains(chatID)) return false;
        Challenge challenge = new Challenge();
        challenge.setChatID(chatPageCache.getCurrentPage(chatID).getContent().get(selectedNumber).getTelegramID());
        challengeCache.put(chatID, challenge);
        return true;
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
