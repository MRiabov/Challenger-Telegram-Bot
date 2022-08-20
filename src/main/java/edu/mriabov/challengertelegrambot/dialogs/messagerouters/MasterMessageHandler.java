package edu.mriabov.challengertelegrambot.dialogs.messagerouters;

import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public final class MasterMessageHandler implements MessageHandler {
    //todo finish
    private final CommandHandler commandHandler;
    private final SubMessageHandler messageHandler;

    public void handleMessages(Update update){
        if (update.getMessage().getText().startsWith(TelegramUtils.COMMAND_PREFIX)) {
            commandHandler.handleMessages(update);
        } else messageHandler.handleMessages(update);

    }
//how lookup works... give a message options to the user.
}
