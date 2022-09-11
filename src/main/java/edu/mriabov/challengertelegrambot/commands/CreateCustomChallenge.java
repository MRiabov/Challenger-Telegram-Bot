package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateCustomChallenge extends BotCommand implements Command {

    private final SenderService senderService;
    private final UserService userService;
    private final GroupService groupService;

    @Override
    public String alias() {
        return "/custom";
    }

    @Override
    public String scope() {
        return new BotCommandScopeAllGroupChats().getType();
    }

    @Override
    public void execute(Message message) {
        if (!userService.existsByTelegramId(message.getFrom().getId())) senderService.replyToMessage(message,
                String.format(Replies.USER_NOT_REGISTERED.text, TelegramUtils.linkBuilder(message.getChatId())));
        Challenge challenge = createChallenge(message);
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
        senderService.replyToMessage(message, "SUCCESS. The operation will take ... coins.");
    }

    private Challenge createChallenge(Message message) {
        Challenge challenge = TelegramUtils.challengeBasicInfo(message);
        //difficulty, area, user, message
        challenge.setCreatedBy(userService.getUserByTelegramId(message.getFrom().getId()).get());
        challenge.setUsers(getMentionedUsers(message, challenge));
        challenge.setDescription(message.getText().substring(getOffset(message)));
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            return challenge;
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        return challenge;
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
}
