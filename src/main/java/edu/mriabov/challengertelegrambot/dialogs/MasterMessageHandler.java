package edu.mriabov.challengertelegrambot.dialogs;

import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MasterMessageHandler {
//todo messageRouting
    //todo extract chatIDs from messages(?? ill need to use person IDs later)


    public void routeMessages(Update update){
        String message=update.getMessage().getText();

        if (message.startsWith(TelegramUtils.COMMAND_PREFIX))


    }

}
