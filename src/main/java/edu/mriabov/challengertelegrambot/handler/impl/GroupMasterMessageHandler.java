package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class GroupMasterMessageHandler implements MessageHandler {
    @Override
    public void handleMessages(Update update) {
        if (update.getMessage().isCommand()){

        }
    }
}
