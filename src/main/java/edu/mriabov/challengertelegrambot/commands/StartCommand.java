package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final SenderService senderService;
    private final UserRepository userRepository;
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
        if (!userRepository.existsByTelegramId(message.getChatId())) registrationService.registerUser(message);
        senderService.sendMessages(message.getChatId(), Buttons.ON_START_NEW_USER);
    }
}
