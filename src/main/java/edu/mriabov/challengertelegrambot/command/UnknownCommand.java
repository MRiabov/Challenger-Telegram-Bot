package edu.mriabov.challengertelegrambot.command;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
@RequiredArgsConstructor
public final class UnknownCommand implements Command {

    final MessageSenderService messageSenderService;

    @Override
    public String alias() {
        return "/unknownCommand";
    }

    @Override
    public void execute(Update update) {
        messageSenderService.sendMessage(update.getMessage().getChatId(), Buttons.UNKNOWN_COMMAND);
    }
}
