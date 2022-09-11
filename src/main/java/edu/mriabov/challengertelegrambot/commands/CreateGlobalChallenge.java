package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllChatAdministrators;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CreateGlobalChallenge implements Command{
    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    @Override
    public String alias() {
        return "/global";
    }

    @Override
    public String scope() {
        return new BotCommandScopeAllChatAdministrators().getType();
    }

    @Override
    public void execute(Message message) {
        Optional<User> creator = userService.getUserByTelegramId(message.getFrom().getId());
        if (creator.isEmpty()) {
            senderService.replyToMessage(message, Replies.USER_NOT_REGISTERED.text);
            return;
        }
        //todo validate that the user is admin.
        Challenge challenge = TelegramUtils.challengeBasicInfo(message.getText());
        challenge.setCreatedBy(creator.get());
    }
}
