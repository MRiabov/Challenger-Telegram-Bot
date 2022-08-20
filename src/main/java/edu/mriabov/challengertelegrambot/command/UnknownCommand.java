package edu.mriabov.challengertelegrambot.command;

import edu.mriabov.challengertelegrambot.dialogs.general.Buttons;
import edu.mriabov.challengertelegrambot.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

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
