package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.HashSet;

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
        when(challengeRepository.findByRecurringTimeIsBetween((LocalTime) any(), (LocalTime) any()))
                .thenReturn(new HashSet<>());

        // Act
        dailyTaskCreator.checkForDailyChallenges();
        verifyNoInteractions(groupRepository);
    }
}

