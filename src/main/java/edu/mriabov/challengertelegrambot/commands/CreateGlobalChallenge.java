package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

import static edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils.isUserAdmin;

@Slf4j
@RequiredArgsConstructor
public class CreateGlobalChallenge implements IBotCommand {
    private final UserService userService;
    private final SenderService senderService;

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
        Optional<User> creator = userService.getUserByTelegramId(message.getFrom().getId());
        if (creator.isEmpty()) {
            senderService.replyToMessage(message, Replies.USER_NOT_REGISTERED.text);
            return;
        }
        if (!isUserAdmin(absSender, message)) {
            senderService.replyToMessage(message, Replies.USER_NOT_ADMIN.text);
            return;
        }
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(creator.get());
    }
}