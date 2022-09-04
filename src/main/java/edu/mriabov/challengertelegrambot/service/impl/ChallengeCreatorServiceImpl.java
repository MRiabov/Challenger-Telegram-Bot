package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeCreatorServiceImpl implements ChallengeCreatorService {

    private final UserService userService;
    private final ChatService chatService;
    private final ChallengeCache challengeCache;
    private final UserPageCache userPageCache;
    private final ChatPageCache chatPageCache;

    @Override
    public void fillUserPageCache(long chatID) {
        userPageCache.put(chatID, chatService.findAllByTelegramID(chatID, 1));
    }

    @Override
    public void fillChatPageCache(long chatID) {
        chatPageCache.put(chatID, userService.findChatsByTelegramId(chatID, 1));
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
        username = username.substring(1);
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.info("received " + username + " from " + chatID + ", but there's no registered user like that.");
            return false;
        }
        Page<Chat> chats = userService.findMatchingChats(chatID, userOptional.get().getTelegramId());
        log.info("received @" + username + " from " + chatID + ", found user with ID: " +
                userOptional.get().getTelegramId() + ", there are " + chats.getTotalElements() + " elements");
        if (chats.getTotalElements()==0) return false;
        Challenge challenge = new Challenge();
        challenge.setUsers(Set.of(userOptional.get()));
        challenge.setChatID(userService.findMatchingChats(chatID, userOptional.get().getTelegramId())
                .getContent().get(0).getTelegramID());
        // FIXME: 9/4/2022 make an actual page, if there is more then one.
        challengeCache.put(chatID, challenge);
        return true;
    }

    private boolean deletedFromCache(long chatID) {
        return !challengeCache.contains(chatID);
    }
}
