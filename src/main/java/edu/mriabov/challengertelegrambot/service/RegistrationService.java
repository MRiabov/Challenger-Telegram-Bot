package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public interface RegistrationService {

    void registerUser(User telegramUser);
    void registerChat(Update update);
    void linkUserToGroup(long userID, long groupID);

}
