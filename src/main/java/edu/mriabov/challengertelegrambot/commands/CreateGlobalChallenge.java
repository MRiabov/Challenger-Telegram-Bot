package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Slf4j
public class CreateGlobalChallenge extends BotCommand {
    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    private Message message;

    public CreateGlobalChallenge(UserService userService, GroupService groupService, SenderService senderService) {
        super("global", "ADMINS ONLY, set a challenge for the entire chat");
        this.userService = userService;
        this.groupService = groupService;
        this.senderService = senderService;
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] arguments) {
        Optional<User> creator = userService.getUserByTelegramId(user.getId());
        if (creator.isEmpty()) {
            senderService.replyToMessage(message, Replies.USER_NOT_REGISTERED.text);
            return;
        }
        absSender.execute(GetChatAdministrators.builder().chatId(chat.getId()).build());
        //todo validate that the user is admin.
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(creator.get());
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        this.message=message;
        super.processMessage(absSender, message, arguments);
    }
}