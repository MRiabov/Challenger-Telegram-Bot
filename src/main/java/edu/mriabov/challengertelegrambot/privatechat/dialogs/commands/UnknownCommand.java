package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class UnknownCommand implements PrivateCommand {

    private final SenderService senderService;

    @Override
    public String alias() {
        return "/unknownCommand";
    }

    @Override
    public void execute(Message message) {
        senderService.sendMessages(message.getChatId(), Buttons.UNKNOWN_COMMAND);
    }
}
