package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.cache.impl.ChallengeCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.challengeBasicInfo;
import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.getOffset;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateGlobalChallenge implements IBotCommand {
    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    private final ChallengeCache challengeCache;
    private final ValidatorService validatorService;

    @Override
    public String getCommandIdentifier() {
        return "global";
    }

    @Override
    public String getDescription() {
        return "ADMINS ONLY, set a challenge for the entire chat";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Optional<User> userByTelegramId = userService.getUserByTelegramId(message.getFrom().getId());
        if (validatorService.isNotGroupChat(message) ||
                validatorService.isNotRegistered(message, userByTelegramId) ||
                validatorService.isNotAdmin(message, absSender)) return;
        Challenge challenge = getChallenge(message, arguments, userByTelegramId);
        if (validatorService.isChallengeInvalid(challenge)) {
            senderService.replyToMessage(message, Replies.INVALID_GLOBAL_CHALLENGE.text);
            return;
        }

        challengeCache.put(message.getFrom().getId(), challenge);
        senderService.replyToMessage(message, Replies.CONFIRM_CHALLENGE.text);
    }

    @NotNull
    private Challenge getChallenge(Message message, String[] arguments, Optional<User> userByTelegramId) {
        Challenge challenge = challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(getOffset(message.getText())));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setExpiresAt(LocalDateTime.now().plusHours(24));
        challenge.setFree(true);
        return challenge;
    }
}