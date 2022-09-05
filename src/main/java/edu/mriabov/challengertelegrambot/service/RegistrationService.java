package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface RegistrationService {

    void registerUser(Message message);
    void registerChat(Update update);

}
