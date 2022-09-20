package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DailyTaskCreator {

    private final ChallengeRepository challengeRepository;
    private final GroupRepository groupRepository;

    @Async
    @Scheduled(fixedRate = 60000)
    public void checkForDailyChallenges() {
        for (Challenge challenge : challengeRepository.findByRecurringTimeIsBetween(LocalTime.now(), LocalTime.now().plusSeconds(60))) {
            challenge.setUsers(groupRepository.findAllUsersByTelegramID(challenge.getGroup().getTelegramId()));
            challengeRepository.save(challenge);
        }
    }
}