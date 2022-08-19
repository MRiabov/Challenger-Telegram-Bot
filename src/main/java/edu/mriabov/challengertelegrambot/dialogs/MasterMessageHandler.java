package edu.mriabov.challengertelegrambot.dialogs;

import edu.mriabov.challengertelegrambot.command.CommandHandler;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MasterMessageHandler {
    //todo finish
    final CommandHandler commandHandler;

    public void routeMessages(Update update){
        if (update.getMessage().getText().startsWith(TelegramUtils.COMMAND_PREFIX)) {
            commandHandler.routeCommands(update);
        } else 


            //find them,
    }

}
