package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface InlineChallengeCreatorService {

    void createChallenge(Message message);
}
