package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartCommand implements PrivateCommand {

    private final SenderService senderService;
    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    @Override
    public String alias() {
        return "/start";
    }

    @Override
    public void execute(Message message) {
        if (!userRepository.existsByTelegramId(message.getChatId())) {
            registrationService.register(message);
        }
        senderService.sendMessages(message.getChatId(), Buttons.ON_START_NEW_USER);
    }
}
