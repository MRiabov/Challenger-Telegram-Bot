package edu.mriabov.challengertelegrambot.dao.daoservice.impl;

import edu.mriabov.challengertelegrambot.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.ChallengeService;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeCreatorServiceImpl implements ChallengeCreatorService {

    private final UserService userService;
    private final GroupService chatService;
    private final ChallengeCache challengeCache;
    private final UserPageCache userPageCache;
    private final ChatPageCache chatPageCache;
    private final BillingService billingService;
    private final ChallengeService challengeService;
    private final SenderService senderService;

    @Override
    public void fillUserPageCache(long userID, Group group) {
        userPageCache.put(userID, chatService.findUsersByTelegramID(userID, group.getTelegramId(), 0));
    }

    @Override
    public void fillChatPageCache(long userID) {
        chatPageCache.put(userID, userService.findChatsByTelegramId(userID, 0));
    }

    @Override
    public void selectUsers(long thisUserID, User otherUser) {
        if (deletedFromCache(thisUserID)) return;
        if (challengeCache.get(thisUserID).getUsers() != null) challengeCache.get(thisUserID).getUsers().add(otherUser);
        else challengeCache.get(thisUserID).setUsers(Set.of(otherUser));
    }

    @Override
    public Optional<Group> selectChats(long userID, int selectedNumber) {
        if (!chatPageCache.contains(userID)) return Optional.empty();
        Challenge challenge = new Challenge();
        Group group;
        challenge.setGroup(group = chatPageCache.getCurrentPage(userID).getContent().get(selectedNumber));
        challengeCache.put(userID, challenge);
        return Optional.ofNullable(group);
    }

    @Override
    public void selectDifficulty(long userID, Difficulty difficulty) {
        if (deletedFromCache(userID)) return;
        challengeCache.get(userID).setExpiresAt(LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        challengeCache.get(userID).setDifficulty(difficulty);
    }

    @Override
    public void selectArea(long chatID, Area area) {
        if (deletedFromCache(chatID)) return;
        challengeCache.get(chatID).setArea(area);
    }

    @Override
    public void setDescription(long userID, String message) {
        if (deletedFromCache(userID)) return;
        challengeCache.get(userID).setDescription(message);
    }

    @Override
    public boolean selectUsersByUsername(long userID, String username) {
        username = username.substring(1);
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.info("received " + username + " from " + userID + ", but there's no registered user like that.");
            return false;
        }
        Page<Group> chats = userService.findMatchingChats(userID, userOptional.get().getTelegramId());
        log.info("received @" + username + " from " + userID + ", found user with ID: " +
                userOptional.get().getTelegramId() + ", there are " + chats.getTotalElements() + " elements");
        if (chats.getTotalElements() == 0) return false;
        Challenge challenge = new Challenge();
        challenge.setUsers(Set.of(userOptional.get()));
        challenge.setGroup(userService.findMatchingChats(userID, userOptional.get().getTelegramId())
                .getContent().get(0));
        // FIXME: 9/4/2022 make an actual page, if there is more then one.
        challengeCache.put(userID, challenge);
        return true;
    }

    @Override
    public Group getSelectedGroupID(long userID) {
        return challengeCache.get(userID).getGroup();
    }

    @Override
    public boolean confirm(long userID) {
        Challenge challenge = challengeCache.get(userID);
        if (challenge.getDifficulty() == null || challenge.getArea() == null || challenge.getUsers() == null)
            return false;
        int price = billingService.challengePrice(challenge);
        if (!billingService.isEnoughCoins(userID, price)) return false;
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setCreatedBy(userService.getUserByTelegramId(userID).get());
        challengeService.save(challenge);
        billingService.billCoins(userID, price);
        challenge.getUsers().forEach(user -> senderService.sendMessages(user.getTelegramId(), Buttons.ASSIGNED_NEW_CHALLENGE));
        return true;
    }

    @Override
    public void selectGoalLength(long userID, int lengthInWeeks) {
        Challenge challenge = new Challenge();
        challenge.setUsers(Set.of(userService.getUserByTelegramId(userID).get()));
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setExpiresAt(LocalDateTime.now().plusWeeks(lengthInWeeks));
        challenge.setFree(true);
        challenge.setDifficulty(Difficulty.GOAL);
        challengeCache.put(userID,challenge);
    }

    @Override
    public boolean skipChallenge(long userID) {
        if (deletedFromCache(userID)) return false;
        Challenge challenge = challengeCache.get(userID);
        return userService.skipChallenge(userID, challenge);
    }

    private boolean deletedFromCache(long chatID) {
        return !challengeCache.contains(chatID);
    }
}
