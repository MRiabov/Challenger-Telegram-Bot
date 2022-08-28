package edu.mriabov.challengertelegrambot.dialogs.commands;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command{

    private final SenderService senderService;

    @Override
    public String alias() {
        return "/start";
    }

    @Override
    public void execute(Update update) {
        senderService.sendMessages(update.getMessage().getChatId(), Buttons.ON_START_NEW_USER);
    }
}
