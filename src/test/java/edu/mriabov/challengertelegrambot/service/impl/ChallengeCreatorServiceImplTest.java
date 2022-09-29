package edu.mriabov.challengertelegrambot.service.impl;

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
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ChallengeCreatorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ChallengeCreatorServiceImplTest {
    @MockBean
    private BillingService billingService;

    @MockBean
    private ChallengeCache challengeCache;

    @Autowired
    private ChallengeCreatorServiceImpl challengeCreatorServiceImpl;

    @MockBean
    private ChallengeService challengeService;

    @MockBean
    private ChatPageCache chatPageCache;

    @MockBean
    private GroupService groupService;

    @MockBean
    private SenderService senderService;

    @MockBean
    private UserPageCache userPageCache;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ChallengeCreatorServiceImpl#selectUsersByUsername(long, String)}
     */
    @Test
    void noMatchingChatsNoUserSelected() {
        // Arrange
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userService.findMatchingChats(anyLong(), anyLong())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(userService.getUserByUsername((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertFalse(challengeCreatorServiceImpl.selectUsersByUsername(1L, "janedoe"));
        verify(userService).getUserByUsername((String) any());
        verify(userService).findMatchingChats(anyLong(), anyLong());
    }

    /**
     * Method under test: {@link ChallengeCreatorServiceImpl#confirm(long)}
     */
    @Test
    void confirmsValidChallenge() {
        // Arrange
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        when(userService.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        UserStats userStats1 = fillUserStats();
        User user1 = fillUser(userStats1);
        Group group = fillGroup();
        Challenge challenge = fillChallenge(user1, group);

        when(challengeCache.get((Long) any())).thenReturn(challenge);
        when(billingService.billCoins(anyLong(), anyInt())).thenReturn(true);
        when(billingService.isEnoughCoins(anyLong(), anyInt())).thenReturn(true);
        when(billingService.challengePrice((Challenge) any())).thenReturn(3);
        doNothing().when(challengeService).save((Challenge) any());

        // Act and Assert
        assertTrue(challengeCreatorServiceImpl.confirm(1L));
        verify(userService).getUserByTelegramId(anyLong());
        verify(challengeCache).get((Long) any());
        verify(billingService).billCoins(anyLong(), anyInt());
        verify(billingService).isEnoughCoins(anyLong(), anyInt());
        verify(billingService).challengePrice(challenge);
        verify(challengeService).save(challenge);
    }
    @Test
    void invalidChallengeNotConfirmed() {
        // Arrange
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        when(userService.getUserByTelegramId(anyLong())).thenReturn(ofResult);
        Challenge challenge = new Challenge();

        when(challengeCache.get((Long) any())).thenReturn(challenge);
        when(billingService.billCoins(anyLong(), anyInt())).thenReturn(true);
        when(billingService.isEnoughCoins(anyLong(), anyInt())).thenReturn(true);
        when(billingService.challengePrice((Challenge) any())).thenReturn(3);
        doNothing().when(challengeService).save((Challenge) any());

        // Act and Assert
        assertFalse(challengeCreatorServiceImpl.confirm(1L));
        verifyNoInteractions(challengeService);
    }

    @NotNull
    private static Challenge fillChallenge(User user1, Group group) {
        Challenge challenge = new Challenge();
        challenge.setArea(Area.FINANCES);
        challenge.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setCreatedBy(user1);
        challenge.setDescription("The characteristics of someone or something");
        challenge.setDifficulty(Difficulty.EASY);
        challenge.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setFree(true);
        challenge.setGroup(group);
        challenge.setId(1);
        challenge.setRecurringTime(LocalTime.of(1, 1));
        challenge.setUsers(new HashSet<>());
        return challenge;
    }

    /**
     * Method under test: {@link ChallengeCreatorServiceImpl#confirm(long)}
     */
    @Test
    void notEnoughCoinsNoChallengeCreated() {
        // Arrange
        when(userService.getUserByTelegramId(anyLong())).thenReturn(null);

        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        Group group = fillGroup();

        Challenge challenge = fillChallenge(user, group);
        when(challengeCache.get((Long) any())).thenReturn(challenge);
        when(billingService.billCoins(anyLong(), anyInt())).thenReturn(false);
        when(billingService.isEnoughCoins(anyLong(), anyInt())).thenReturn(false);
        when(billingService.challengePrice((Challenge) any())).thenReturn(3);
        doNothing().when(challengeService).save((Challenge) any());

        //Act and Assert
        assertThat(challengeCreatorServiceImpl.confirm(1L)).isFalse();
    }

    @NotNull
    private static UserStats fillUserStats() {
        UserStats userStats = new UserStats();
        userStats.setFinances(1);
        userStats.setFitness(1);
        userStats.setId(1);
        userStats.setMindfulness(1);
        userStats.setRelationships(1);
        return userStats;
    }

    /**
     * Method under test: {@link ChallengeCreatorServiceImpl#selectUsersByUsername(long, String)}
     */
    @Test
    void findsExistingUser() {
        // Arrange
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        Group group = fillGroup();

        ArrayList<Group> groupList = new ArrayList<>();
        groupList.add(group);
        PageImpl<Group> pageImpl = new PageImpl<>(groupList);
        when(userService.findMatchingChats(anyLong(), anyLong())).thenReturn(pageImpl);
        when(userService.getUserByUsername((String) any())).thenReturn(ofResult);
        doNothing().when(challengeCache).put((Long) any(), (Challenge) any());

        // Act and Assert
        assertTrue(challengeCreatorServiceImpl.selectUsersByUsername(1L, "janedoe"));
        verify(userService).getUserByUsername((String) any());
        verify(userService, atLeast(1)).findMatchingChats(anyLong(), anyLong());
        verify(challengeCache).put((Long) any(), (Challenge) any());
    }

    @NotNull
    private static Group fillGroup() {
        Group group = new Group();
        group.setChallenges(new HashSet<>());
        group.setGroupName("Group Name");
        group.setId(1);
        group.setTelegramId(123L);
        group.setTotalTasksCompleted(1);
        group.setUsers(new HashSet<>());
        return group;
    }

    @NotNull
    private static User fillUser(UserStats userStats) {
        User user = new User();
        user.setChallenges(new HashSet<>());
        user.setCoins(1);
        user.setCreatedChallenges(new HashSet<>());
        user.setFirstName("Jane");
        user.setGroups(new HashSet<>());
        user.setId(1);
        user.setLastName("Doe");
        user.setTelegramId(123L);
        user.setUserStats(userStats);
        user.setUsername("janedoe");
        return user;
    }

    /**
     * Method under test: {@link ChallengeCreatorServiceImpl#selectUsersByUsername(long, String)}
     */
    @Test
    void inexistentUserNotFound() {
        // Arrange
        Group group = fillGroup();

        ArrayList<Group> groupList = new ArrayList<>();
        groupList.add(group);
        PageImpl<Group> pageImpl = new PageImpl<>(groupList);
        when(userService.findMatchingChats(anyLong(), anyLong())).thenReturn(pageImpl);
        when(userService.getUserByUsername((String) any())).thenReturn(Optional.empty());
        doNothing().when(challengeCache).put((Long) any(), (Challenge) any());

        // Act and Assert
        assertFalse(challengeCreatorServiceImpl.selectUsersByUsername(1L, "janedoe"));
        verify(userService).getUserByUsername((String) any());
    }
}

