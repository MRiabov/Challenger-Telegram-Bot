package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CreateCustomChallenge implements IBotCommand {

    private final SenderService senderService;
    private final UserService userService;
    private final GroupService groupService;
    private final ChallengeCache challengeCache;

    public CreateCustomChallenge(SenderService senderService, UserService userService, GroupService groupService, ChallengeCache challengeCache) {
        this.senderService = senderService;
        this.userService = userService;
        this.groupService = groupService;
        this.challengeCache = challengeCache;
    }

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
        Optional<User> userByTelegramId = userService.getUserByTelegramId(message.getFrom().getId());
        if (message.getChat().isUserChat()) {
            senderService.sendMessages(message.getChatId(), Replies.WRONG_CHAT_TYPE.text);
        }
        if (userByTelegramId.isEmpty()) senderService.replyToMessage(message, Replies.USER_NOT_REGISTERED.text);
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setUsers(getMentionedUsers(message, challenge));
        challenge.setDescription(message.getText().substring(getOffset(message)));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setCreatedAt(Instant.now());
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
        else {
            senderService.replyToMessage(message, "SUCCESS. The operation will take ... coins.");
            challengeCache.put(message.getFrom().getId(), challenge);
        }
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
