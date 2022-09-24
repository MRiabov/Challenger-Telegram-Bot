package edu.mriabov.challengertelegrambot.dao.daoservice.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@RequiredArgsConstructor

public class ValidatorServiceImpl implements ValidatorService {

    private final UserService userService;
    private final SenderService senderService;

    @Override
    public boolean isNotRegistered(long userID) {
        return userService.getUserByTelegramId(userID).isEmpty();
    }

    @Override
    public boolean isNotRegistered(Message message, Optional<User> userOptional) {
        if (userOptional.isPresent()) return false;
        senderService.replyToMessage(message, String.format(Replies.USER_NOT_REGISTERED.text,
                TelegramUtils.linkBuilder(message.getChatId())));
        return true;
    }

    @Override
    public boolean isChatLinked(long userID) {
        return false;
    }

    @Override
    public boolean isUserChat(Message message) {
        if (message.getChat().isUserChat()) {
            senderService.replyToMessage(message, Replies.WRONG_CHAT_TYPE.text);
            return false;
        }
        return true;
    }

    @Override
    public boolean isGroupChat(Message message) {
        if (message.getChat().isUserChat()) {
            senderService.replyToMessage(message, Replies.WRONG_CHAT_TYPE.text);
            return false;
        }
        return true;
    }

    @Override
    public boolean isChallengeInvalid(Challenge challenge) {
        return challenge.getDifficulty() == null && challenge.getUsers().size() == 0 && challenge.getArea() == null;
    }
}
