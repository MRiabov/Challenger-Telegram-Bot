package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class InlineChallengeCreatorServiceImpl implements InlineChallengeCreatorService {

    private final ChatService chatService;
    private final UserService userService;

    @Override
    public void createChallenge(Message message) {
        Challenge challenge = new Challenge();
        //user, difficulty, area, message
        Set<User> userSet = new HashSet<>();
        for (MessageEntity entity : message.getEntities())
            userSet.add(userService.getUserByTelegramId(entity.getUser().getId()));
        challenge.setUsers(userSet);
    }
}
