package edu.mriabov.challengertelegrambot.dao.daoservice.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @NotNull
    private static User fillUser1(UserStats userStats1) {
        User user1 = new User();
        user1.setChallenges(new HashSet<>());
        user1.setCoins(1);
        user1.setCreatedChallenges(new HashSet<>());
        user1.setFirstName("Jane");
        user1.setGroups(new HashSet<>());
        user1.setId(1);
        user1.setLastName("Doe");
        user1.setTelegramId(123L);
        user1.setUserStats(userStats1);
        user1.setUsername("janedoe");
        return user1;
    }

    @NotNull
    private static Optional<User> fillUser(UserStats userStats) {
        User user = fillUser1(userStats);
        return Optional.of(user);
    }

    @NotNull
    private static UserStats fillUserStats() {
        UserStats userStats1 = new UserStats();
        userStats1.setFinances(1);
        userStats1.setFitness(1);
        userStats1.setId(1);
        userStats1.setMindfulness(1);
        userStats1.setRelationships(1);
        return userStats1;
    }

    @NotNull
    private static Group fillGroup() {
        Group group1 = new Group();
        group1.setChallenges(new HashSet<>());
        group1.setGroupName("Group Name");
        group1.setId(1);
        group1.setTelegramId(123L);
        group1.setTotalTasksCompleted(1);
        group1.setUsers(new HashSet<>());
        return group1;
    }

    @NotNull
    private static Challenge fillChallenge(User user2, Group group1) {
        Challenge challenge = new Challenge();
        challenge.setArea(Area.FINANCES);
        challenge.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setCreatedBy(user2);
        challenge.setDescription("The characteristics of someone or something");
        challenge.setDifficulty(Difficulty.EASY);
        challenge.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setFree(true);
        challenge.setGroup(group1);
        challenge.setId(1);
        challenge.setRecurringTime(LocalTime.of(1, 1));
        challenge.setUsers(Set.of(user2));
        return challenge;
    }

    /**
     * Method under test: {@link UserServiceImpl#completeChallenge(long, Challenge)}
     */
    @Test
    void completingChallengeIncreasesCoinsAndStats() {
        // Arrange
        UserStats userStats = fillUserStats();
        Optional<User> ofResult = fillUser(userStats);
        User user1 = fillUser1(userStats);
        when(userRepository.save(any())).thenReturn(user1);
        when(userRepository.getAllChallengesButOne(anyLong(), anyInt())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        Group group = fillGroup();
        when(groupRepository.save(any())).thenReturn(group);

        int startingCoins = 0;
        Challenge challenge = fillChallenge(user1, group);

        Difficulty challengeDifficulty = Difficulty.EASY;
        Area challengeArea = Area.FITNESS;
        challenge.setDifficulty(challengeDifficulty);
        challenge.setArea(challengeArea);

        // Act
        userServiceImpl.completeChallenge(1L, challenge);

        // Assert
        verify(userRepository).save(any());
        verify(userRepository).getUserByTelegramId(anyLong());
        verify(userRepository).getAllChallengesButOne(anyLong(), anyInt());
        verify(groupRepository).save(any());
        assertEquals(startingCoins + challengeDifficulty.price, userStats.getFitness());
        assertEquals(startingCoins + challengeDifficulty.reward, user1.getCoins());
        assertEquals(2, challenge.getGroup().getTotalTasksCompleted());
    }
}

