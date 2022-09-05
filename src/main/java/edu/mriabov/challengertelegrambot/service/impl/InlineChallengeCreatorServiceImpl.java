package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class InlineChallengeCreatorServiceImpl implements InlineChallengeCreatorService {

    private final ChatService chatService;
    private final UserService userService;

    @Override
    public Challenge createChallenge(Message message) {
        Challenge challenge = new Challenge();
        //user, difficulty, area, message
        Set<User> userSet = new HashSet<>();
        for (MessageEntity entity : message.getEntities()) {
            if (challenge.getDescription()!=null) break;
            switch (entity.getType()) {
                case EntityType.MENTION -> userService.getUserByUsername(entity.getText()).ifPresent(userSet::add);
                case EntityType.TEXTMENTION ->
                        userService.getUserByTelegramId(entity.getUser().getId()).ifPresent(userSet::add);
                default -> parametersForChallenge(message, challenge, entity);
            }
        }
        challenge.setUsers(userSet);
        if (challenge.getDifficulty()==null||challenge.getUsers().size()==0||challenge.getArea()==null) return new Challenge();
        //todo createdBy
        challenge.setChatID(message.getChatId());
        challenge.setCreatedAt(LocalDateTime.now());
        return challenge;
    }

    private static void parametersForChallenge(Message message, Challenge challenge, MessageEntity entity) {
        switch (message.getText().toLowerCase()) {
            case "easy" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.EASY);
            }
            case "medium" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.MEDIUM);
            }
            case "difficult" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.DIFFICULT);
            }
            case "goal" -> {
                if (challenge.getDifficulty() == null) challenge.setDifficulty(Difficulty.GOAL);
            }
            case "fitness" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.FITNESS);
            }
            case "relationships" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.RELATIONSHIPS);
            }
            case "finances" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.FINANCES);
            }
            case "mindfulness" -> {
                if (challenge.getArea() == null) challenge.setArea(Area.MINDFULNESS);
            }
            default ->
                    challenge.setDescription(message.getText().substring(entity.getOffset() + entity.getLength()));
        }
    }
}
