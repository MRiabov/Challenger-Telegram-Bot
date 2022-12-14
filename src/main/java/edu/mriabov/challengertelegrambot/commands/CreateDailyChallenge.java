package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.cache.impl.ChallengeCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateDailyChallenge implements IBotCommand {

    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    private final ChallengeCache challengeCache;
    private final ValidatorService validatorService;

    private LocalTime getChallengeTime(String[] arguments) {
        for (String word : arguments) {
            if (word.matches("([01]?\\d|2[0-3]):[0-5]\\d"))
                return LocalTime.of(Integer.parseInt(word.substring(0, 2)), Integer.parseInt(word.substring(4, 5)));
        }
        return null;
    }

    @Override
    public String getCommandIdentifier() {
        return "daily";
    }

    @Override
    public String getDescription() {
        return "create a recurring challenge. Type /help to know how.";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Optional<User> userByTelegramId = userService.getUserByTelegramId(message.getFrom().getId());
        if (validatorService.isNotGroupChat(message) ||
                validatorService.isNotRegistered(message, userByTelegramId) ||
                validatorService.isNotAdmin(message, absSender)) return;
        validatorService.linkChatsIfNotLinked(message.getFrom().getId(), message.getChatId());
        Challenge challenge = getChallenge(message, arguments, userByTelegramId);
        if (arguments.length < 3 || validatorService.isChallengeInvalid(challenge) || challenge.getRecurringTime() == null) {
            senderService.replyToMessage(message, Replies.INVALID_DAILY_CHALLENGE.text);
            return;
        }
        challengeCache.put(message.getFrom().getId(), challenge);
        senderService.replyToMessage(message, Replies.CONFIRM_CHALLENGE.text);
    }

    @NotNull
    private Challenge getChallenge(Message message, String[] arguments, Optional<User> userByTelegramId) {
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setRecurringTime(getChallengeTime(arguments));
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(TelegramUtils.getOffset(message.getText())));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setFree(true);
        return challenge;
    }
}
