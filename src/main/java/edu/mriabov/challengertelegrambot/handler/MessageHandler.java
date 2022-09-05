package edu.mriabov.challengertelegrambot.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    void handleMessages(Message message);

}
