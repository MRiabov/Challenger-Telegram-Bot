package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.service.MessageHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageHandlerImpl implements MessageHandler {

    @Override
    public void handleMessages(Update update) {
        String message = update.getMessage().getText();
        if (message.startsWith("/"));
        if (message.startsWith("\\"));


    }
}
