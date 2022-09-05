package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InlineChallengeCreatorService {

    Challenge createChallenge(Message message);

}
