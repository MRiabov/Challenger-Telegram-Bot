package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.cache.ChallengeCache;
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
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateCustomChallenge implements IBotCommand {

    private final SenderService senderService;
    private final UserService userService;
    private final GroupService groupService;
    private final ChallengeCache challengeCache;
    private final ValidatorService validatorService;

    @Override
    public String getCommandIdentifier() {
        return "custom";
    }

    @Override
    public String getDescription() {
        return "Create a custom challenge";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (validatorService.isNotGroupChat(message)) return;
        Optional<User> userByTelegramId = userService.getUserByTelegramId(message.getFrom().getId());
        if (validatorService.isNotRegistered(message, userByTelegramId)) return;
        validatorService.linkChatsIfNotLinked(message.getFrom().getId(),message.getChatId());
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setUsers(getMentionedUsers(message, challenge));
        challenge.setDescription(message.getText().substring(getOffset(message)));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setExpiresAt(LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        if (validatorService.isChallengeInvalid(challenge)) {
            senderService.replyToMessage(message,Replies.INVALID_CUSTOM_CHALLENGE.text);
            return;
        }
        challengeCache.put(message.getFrom().getId(), challenge);
        senderService.replyToMessage(message, Replies.CONFIRM_CHALLENGE.text);
        log.info("Custom challenge was successfully created with args " + message.getText());
    }

    private int getOffset(Message message) {
        int offset = 0;
        for (MessageEntity entity : message.getEntities())
            if (entity.getType().equals(EntityType.MENTION) || entity.getType().equals(EntityType.TEXTMENTION))
                offset = entity.getOffset() + entity.getLength();
        return offset;
    }


    private Set<User> getMentionedUsers(Message message, Challenge challenge) {
        Set<User> userSet = new HashSet<>();
        for (MessageEntity entity : message.getEntities()) {
            if (challenge.getDescription() != null) break;
            switch (entity.getType()) {
                case EntityType.MENTION -> userService.getUserByUsername(entity.getText()).ifPresent(userSet::add);
                case EntityType.TEXTMENTION ->
                        userService.getUserByTelegramId(entity.getUser().getId()).ifPresent(userSet::add);
            }
        }
        return userSet;
    }
}
