package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface InlineChallengeCreatorService {

    Optional<Challenge> createChallenge(Message message);

}
