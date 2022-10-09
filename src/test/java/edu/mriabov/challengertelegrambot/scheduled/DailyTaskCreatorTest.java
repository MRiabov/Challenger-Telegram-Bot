package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DailyTaskCreator.class})
@ExtendWith(SpringExtension.class)
class DailyTaskCreatorTest {
    @MockBean
    private ChallengeRepository challengeRepository;

    @Autowired
    private DailyTaskCreator dailyTaskCreator;

    @MockBean
    private GroupRepository groupRepository;

    /**
     * Method under test: {@link DailyTaskCreator#checkForDailyChallenges()}
     */
    @Test
    void noChallengesNoGroupInteractions() {
        // Arrange
        when(challengeRepository.findByRecurringTimeIsBetween(any(), any()))
                .thenReturn(new HashSet<>());

        // Act
        dailyTaskCreator.checkForDailyChallenges();
        verifyNoInteractions(groupRepository);
    }

    /**
     * Method under test: {@link DailyTaskCreator#checkForDailyChallenges()}
     */
    @Test
    void testCheckForDailyChallenges() {
        // Arrange
        when(challengeRepository.findByRecurringTimeIsBetween(any(), any()))
                .thenReturn(new HashSet<>());

        // Act
        dailyTaskCreator.checkForDailyChallenges();
    }

    /**
     * Method under test: {@link DailyTaskCreator#checkForDailyChallenges()}
     */
    @Test
    void challengeIsSaved() {
        // Arrange
        UserStats userStats = new UserStats();
        userStats.setFinances(1);
        userStats.setFitness(1);
        userStats.setId(1);
        userStats.setMindfulness(1);
        userStats.setRelationships(1);

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

        Group group = new Group();
        group.setChallenges(new HashSet<>());
        group.setGroupName("Group Name");
        group.setId(1);
        group.setTelegramId(123L);
        group.setTotalTasksCompleted(1);
        group.setUsers(new HashSet<>());

        Challenge challenge = new Challenge();
        challenge.setArea(Area.FINANCES);
        challenge.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setCreatedBy(user);
        challenge.setDescription("The characteristics of someone or something");
        challenge.setDifficulty(Difficulty.EASY);
        challenge.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setFree(true);
        challenge.setGroup(group);
        challenge.setId(1);
        challenge.setRecurringTime(LocalTime.of(1, 1));
        challenge.setUsers(new HashSet<>());

        HashSet<Challenge> challengeSet = new HashSet<>();
        challengeSet.add(challenge);

        UserStats userStats1 = new UserStats();
        userStats1.setFinances(1);
        userStats1.setFitness(1);
        userStats1.setId(1);
        userStats1.setMindfulness(1);
        userStats1.setRelationships(1);

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

        Group group1 = new Group();
        group1.setChallenges(new HashSet<>());
        group1.setGroupName("Group Name");
        group1.setId(1);
        group1.setTelegramId(123L);
        group1.setTotalTasksCompleted(1);
        group1.setUsers(new HashSet<>());

        when(challengeRepository.findByRecurringTimeIsBetween(any(), any()))
                .thenReturn(challengeSet);
        when(groupRepository.findAllUsersByTelegramID(anyLong())).thenReturn(Set.of(user1));

        // Act
        dailyTaskCreator.checkForDailyChallenges();

        //Assert
        verify(challengeRepository).save(challenge);
    }
}

