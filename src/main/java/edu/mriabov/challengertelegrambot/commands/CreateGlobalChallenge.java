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
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.getOffset;
import static edu.mriabov.challengertelegrambot.utils.TelegramUtils.isUserAdmin;

@Slf4j
@RequiredArgsConstructor
@Component
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
        if (arguments.length < 3 || challenge.getDifficulty() == null || challenge.getArea() == null) {
            senderService.replyToMessage(message, Replies.INVALID_GLOBAL_CHALLENGE.text);
            return;
        }
        challenge.setDescription(message.getText().substring(getOffset(message.getText())));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setExpiresAt(LocalDateTime.now().plusHours(24));
        challenge.setFree(true);
        senderService.replyToMessage(message, Replies.CONFIRM_CHALLENGE.text);
        challengeCache.put(message.getFrom().getId(), challenge);
    }
}