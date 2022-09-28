package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TaskExpirer.class})
@ExtendWith(SpringExtension.class)
class TaskExpirerTest {
    @MockBean
    private ChallengeRepository challengeRepository;
    @Autowired
    private TaskExpirer taskExpirer;
    @MockBean
    private SenderService senderService;

    /**
     * Method under test: {@link TaskExpirer#checkForExpiration()}
     */
    @Test
    void noGroupsNoInteractions() {
        // Arrange
        when(challengeRepository.findByExpiresAtBetween((LocalDateTime) any(), (LocalDateTime) any()))
                .thenReturn(new HashSet<>());

        // Act
        taskExpirer.checkForExpiration();
        //Assert
        verifyNoInteractions(senderService);
    }

    /**
     * Method under test: {@link TaskExpirer#checkForExpiration()}
     */
    @Test
    void challengeFailed() {
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
        challengeSet.addAll(new ArrayList<>());
        when(challengeRepository.findUsersById(anyInt())).thenReturn(List.of(user));
        doNothing().when(challengeRepository).deleteById((Integer) any());
        when(challengeRepository.findByExpiresAtBetween((LocalDateTime) any(), (LocalDateTime) any()))
                .thenReturn(challengeSet);

        // Act
        taskExpirer.checkForExpiration();

        // Assert
        verify(senderService).sendMessages(anyLong(), eq(Buttons.FAILED_CHALLENGE));
    }
}

