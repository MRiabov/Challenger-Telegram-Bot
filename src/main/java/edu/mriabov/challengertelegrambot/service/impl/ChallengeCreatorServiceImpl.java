package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final BillingService billingService;
    private final ChallengeService challengeService;

    @Override
    public void fillUserPageCache(long userID, Chat group) {
        userPageCache.put(userID, chatService.findUsersByTelegramID(userID, group.getTelegramID(), 0));
    }

    @Override
    public void fillChatPageCache(long userID) {
        chatPageCache.put(userID, userService.findChatsByTelegramId(userID, 0));
    }

    @Override
    public boolean selectUsers(long thisUserID, User otherUser) {
        if (deletedFromCache(thisUserID)) return false;
        if (challengeCache.get(thisUserID).getUsers() != null) challengeCache.get(thisUserID).getUsers().add(otherUser);
        else challengeCache.get(thisUserID).setUsers(Set.of(otherUser));
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
    public boolean selectUsersByUsername(long userID, String username) {
        username = username.substring(1);
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.info("received " + username + " from " + userID + ", but there's no registered user like that.");
            return false;
        }
        Page<Chat> chats = userService.findMatchingChats(userID, userOptional.get().getTelegramId());
        log.info("received @" + username + " from " + userID + ", found user with ID: " +
                userOptional.get().getTelegramId() + ", there are " + chats.getTotalElements() + " elements");
        if (chats.getTotalElements() == 0) return false;
        Challenge challenge = new Challenge();
        challenge.setUsers(Set.of(userOptional.get()));
        challenge.setChat(userService.findMatchingChats(userID, userOptional.get().getTelegramId())
                .getContent().get(0));
        // FIXME: 9/4/2022 make an actual page, if there is more then one.
        challengeCache.put(userID, challenge);
        return true;
    }

    @Override
    public Chat getSelectedGroupID(long userID) {
        return challengeCache.get(userID).getChat();
    }

    @Override
    public boolean confirm(long userID) {
        Challenge challenge = challengeCache.get(userID);
        if (challenge.getDifficulty() == null || challenge.getArea() == null || challenge.getUsers() == null)
            return false;
        int price = billingService.challengePrice(challenge);
        if (billingService.isEnoughCoins(userID, price)) return false;
        challenge.setCreatedAt(LocalDateTime.now());
        //noinspection OptionalGetWithoutIsPresent
        challenge.setCreatedBy(userService.getUserByTelegramId(userID).get());
        challengeService.save(challenge);
        billingService.billCoins(userID, price);
        return true;
    }

    private boolean deletedFromCache(long chatID) {
        return !challengeCache.contains(chatID);
    }
}
