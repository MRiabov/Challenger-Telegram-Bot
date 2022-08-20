package edu.mriabov.challengertelegrambot.dialogs.messagerouters;

import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public final class MasterMessageHandler implements MessageHandler {
    //todo finish
    final CommandHandler commandHandler;
    final SubMessageHandler messageHandler;

    public void routeMessages(Update update){
        if (update.getMessage().getText().startsWith(TelegramUtils.COMMAND_PREFIX)) {
            commandHandler.routeMessages(update);
        } else messageHandler.routeMessages(update);

    }
//how lookup works... give a message options to the user.
}
