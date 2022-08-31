package edu.mriabov.challengertelegrambot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

    void handleMessages(Update update);

}
