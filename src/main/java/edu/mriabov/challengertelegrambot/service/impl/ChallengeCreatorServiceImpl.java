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
    public void fillUserPageCache(long userID, Chat group) {
        userPageCache.put(userID, chatService.findUsersByTelegramID(userID, group.getTelegramID(), 0));
    }

    @Override
    public void fillChatPageCache(long userID) {
        chatPageCache.put(userID, userService.findChatsByTelegramId(userID, 0));
    }

    @Override
    public boolean selectUsers(long userID, User user) {
        if (deletedFromCache(userID)) return false;
        if (challengeCache.get(userID).getUsers()!=null) challengeCache.get(userID).getUsers().add(user);
        else challengeCache.get(userID).setUsers(Set.of(user));
        return true;
    }

    @Override
    public Optional<Chat> selectChats(long userID, int selectedNumber) {
        if (!chatPageCache.contains(userID)) return Optional.empty();
        Challenge challenge = new Challenge();
        Chat chat;
        challenge.setChat(chat = chatPageCache.getCurrentPage(userID).getContent().get(selectedNumber));
        challengeCache.put(userID, challenge);
        return Optional.ofNullable(chat);
    }

    @Override
    public boolean selectDifficulty(long userID, Difficulty difficulty) {
        if (deletedFromCache(userID)) return false;
        challengeCache.get(userID).setDifficulty(difficulty);
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
        if (chats.getTotalElements() == 0) return false;
        Challenge challenge = new Challenge();
        challenge.setUsers(Set.of(userOptional.get()));
        challenge.setChat(userService.findMatchingChats(chatID, userOptional.get().getTelegramId())
                .getContent().get(0));
        // FIXME: 9/4/2022 make an actual page, if there is more then one.
        challengeCache.put(chatID, challenge);
        return true;
    }

    @Override
    public Chat getSelectedGroupID(long userID) {
        return challengeCache.get(userID).getChat();
    }

    private boolean deletedFromCache(long chatID) {
        return !challengeCache.contains(chatID);
    }
}
