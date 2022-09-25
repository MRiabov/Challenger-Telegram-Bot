package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Set;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class DailyTaskCreator {

    private final ChallengeRepository challengeRepository;
    private final GroupRepository groupRepository;

    @Async
    @Scheduled(fixedRate = 60000)
    public void checkForDailyChallenges() {
        Set<Challenge> recurringChallenges = challengeRepository.findByRecurringTimeIsBetween(LocalTime.now(), LocalTime.now().plusSeconds(60));
        for (Challenge challenge : recurringChallenges) {
            challenge.setUsers(groupRepository.findAllUsersByTelegramID(challenge.getGroup().getTelegramId()));
            challengeRepository.save(challenge);
        }
        log.info("Scanned for recurring challenges. Found " + recurringChallenges.size() + " challenges");
    }
}