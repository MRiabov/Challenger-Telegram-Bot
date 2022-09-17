package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InlineChallengeCreatorServiceImpl implements InlineChallengeCreatorService {

    private final GroupService chatService;
    private final UserService userService;

    @Override
    public Optional<Challenge> createChallenge(Message message) {
        return Optional.empty();
    }


}
