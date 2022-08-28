package edu.mriabov.challengertelegrambot.dialogs.commands;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
@RequiredArgsConstructor
public class UnknownCommand implements Command{

    private final SenderService senderService;

    @Override
    public String alias() {
        return "/unknownCommand";
    }

    @Override
    public void execute(Update update) {
        senderService.sendMessages(update.getMessage().getChatId(), Buttons.UNKNOWN_COMMAND);
    }
}
