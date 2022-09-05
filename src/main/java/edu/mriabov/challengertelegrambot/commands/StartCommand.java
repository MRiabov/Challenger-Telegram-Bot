package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final SenderService senderService;
    private final UserService userService;
    private final ChatService chatService;
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
        if (message.getText().length()>6) { //if this is a deep linking request, add the chat from the deep linking request.
            userService.addChat(message.getChatId(),
                    chatService.findByTelegramID(Long.parseLong(message.getText().substring(7))));
        }
    }
}
