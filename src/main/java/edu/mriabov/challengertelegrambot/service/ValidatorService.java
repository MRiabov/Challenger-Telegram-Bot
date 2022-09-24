package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface ValidatorService {

    boolean isNotRegistered(long userID);
    boolean isNotRegistered(Message message, Optional<User> userOptional);

    boolean isChatLinked(long userID);

    boolean isUserChat(Message message);

    boolean isGroupChat(Message message);

    boolean isChallengeInvalid(Challenge challenge);

}
