package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface ValidatorService {

    boolean isRegistered(long userID);
    boolean isRegistered(Message message,Optional<User> userOptional);

    boolean isChatLinked(long userID);

    boolean isUserChat(Message message);

    boolean isGroupChat(Message message);

    boolean isChallengeValid(Challenge challenge);

}
