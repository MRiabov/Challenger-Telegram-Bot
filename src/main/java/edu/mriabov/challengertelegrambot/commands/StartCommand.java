package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartCommand implements Command {

    private final SenderService senderService;
    private final UserService userService;
    private final GroupService groupService;
    private final RegistrationService registrationService;

    @Override
    public String alias() {
        return "/start";
    }

    @Override
    public String scope() {
        return new BotCommandScopeAllPrivateChats().getType();
    }

    @Override
    public void execute(Message message) {
        if (!message.getChat().getType().equals("private")) {
            senderService.sendMessages(message.getChatId(), Replies.WRONG_CHAT_TYPE.text);
            return;
        }
        if (!userService.existsByTelegramId(message.getChatId())) registrationService.registerUser(message);
        senderService.sendMessages(message.getChatId(), Buttons.ON_START_NEW_USER);
        //if this is a deep linking request, add the chat from the deep linking request.
        if (message.getText().length() > 6) addChat(message);
    }

    private void addChat(Message message) {
        boolean chatSuccessfullyLinked = userService.addChat(message.getChatId(),
                groupService.findByTelegramID(Long.parseLong(message.getText().substring(7))));
        if (chatSuccessfullyLinked) {
            log.info("User " + message.getChatId() + " has successfully linked a chat " + message.getText().substring(7));
            senderService.sendMessages(message.getChatId(), Replies.CHAT_SUCCESSFULLY_LINKED.text.formatted(message.getText().substring(7)));
        } else
            log.warn("User " + message.getChatId() + " has failed to add a chat via /start. his payload: " + message.getText().substring(7));
    }
}
