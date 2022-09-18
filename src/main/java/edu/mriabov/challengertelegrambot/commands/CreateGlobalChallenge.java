package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.getOffset;
import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.isUserAdmin;

@Slf4j
@RequiredArgsConstructor
public class CreateGlobalChallenge implements IBotCommand {
    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    private final ChallengeCache challengeCache;

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
        if (userByTelegramId.isEmpty()) {
            senderService.replyToMessage(message, Replies.USER_NOT_REGISTERED.text);
            return;
        }
        if (!isUserAdmin(absSender, message)) {
            senderService.replyToMessage(message, Replies.USER_NOT_ADMIN.text);
            return;
        }
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(getOffset(message.getText())));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setExpiresAt(LocalDateTime.now().plusHours(24));
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
        else {
            senderService.replyToMessage(message, "SUCCESS. The operation is free as it is created by an admin.");
            challengeCache.put(message.getFrom().getId(), challenge);
        }
    }
}