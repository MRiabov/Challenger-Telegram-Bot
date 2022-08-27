package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

    void handleMessages(Update update);

}
