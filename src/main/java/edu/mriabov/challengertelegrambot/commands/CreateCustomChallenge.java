package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateCustomChallenge extends BotCommand {

    private final SenderService senderService;
    private final UserService userService;
    private final GroupService groupService;
    private final ChallengeCache challengeCache;
    private Message message;
    public CreateCustomChallenge(SenderService senderService, UserService userService, GroupService groupService, ChallengeCache challengeCache) {
        super("custom", "Create a custom challenge");
        this.senderService = senderService;
        this.userService = userService;
        this.groupService = groupService;
        this.challengeCache = challengeCache;
    }
    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] arguments) {
        if (!userService.existsByTelegramId(user.getId())) senderService.replyToMessage(message,
                String.format(Replies.USER_NOT_REGISTERED.text, TelegramUtils.linkBuilder(message.getChatId())));
        if (chat.isUserChat()) {
            senderService.sendMessages(chat.getId(), Buttons.USER_NOT_FOUND);
        }
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userService.getUserByTelegramId(user.getId()).get());
        challenge.setUsers(getMentionedUsers(message, challenge));
        challenge.setDescription(message.getText().substring(getOffset(message)));
        challenge.setGroup(groupService.findByTelegramID(chat.getId()));
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
        else {
            senderService.replyToMessage(message, "SUCCESS. The operation will take ... coins.");
            challengeCache.put(message.getFrom().getId(),challenge);
        }
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

    private static int getOffset(Message message) {
        int offset = 0;
        for (MessageEntity entity : message.getEntities())
            if (entity.getType().equals(EntityType.MENTION) || entity.getType().equals(EntityType.TEXTMENTION))
                offset = entity.getOffset() + entity.getLength();
        return offset;
    }
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        this.message=message;
        super.processMessage(absSender, message, arguments);
    }

}
