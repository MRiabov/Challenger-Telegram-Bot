package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class ValidatorServiceImpl implements ValidatorService {

    private final UserService userService;
    private final SenderService senderService;
    private final RegistrationService registrationService;

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
    public void linkChatsIfNotLinked(long userID, long groupID) {
        if (!userService.isInGroup(userID, groupID)) registrationService.linkUserToGroup(userID, groupID);

    }

    @Override
    public boolean isNotUserChat(Message message) {
        if (message.getChat().isUserChat()) return false;
        senderService.replyToMessage(message, Replies.WRONG_CHAT_TYPE.text);
        return true;
    }

    @Override
    public boolean isNotGroupChat(Message message) {
        if (message.getChat().isGroupChat()||message.getChat().isSuperGroupChat()) return false;
        senderService.replyToMessage(message, Replies.WRONG_CHAT_TYPE.text);
        return true;
    }

    @Override
    public boolean isChallengeInvalid(Challenge challenge) {
        return challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null;
    }
}