package edu.mriabov.challengertelegrambot.scheduled;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class TaskExpirer {

    private final ChallengeRepository challengeRepository;
    private final SenderService senderService;

    @Async
    @Scheduled(fixedRate = 60000)
    public void checkForExpiration() {
        for (Challenge challenge : challengeRepository.findByExpiresAtBetween(LocalDateTime.now(), LocalDateTime.now().plusSeconds(60))) {
            List<User> users = challengeRepository.findUsersById(challenge.getId());
            for (User user : users) {
                senderService.sendMessages(user.getTelegramId(), Buttons.FAILED_CHALLENGE);
            }
            if (users.size() > 0)
                senderService.sendMessages(challenge.getGroup().getTelegramId(), failedUsers(users));
            challengeRepository.deleteById(challenge.getId());
        }
    }

    private String failedUsers(List<User> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder
                    .append(user.getUsername()).append(" ")
                    .append(user.getFirstName())
                    .append((user.getLastName() != null) ? (" " + user.getFirstName()) : "")
                    .append("\n");
        }
        return stringBuilder.toString();
    }

}
