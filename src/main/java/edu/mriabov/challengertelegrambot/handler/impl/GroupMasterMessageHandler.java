package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GroupMasterMessageHandler implements MessageHandler {

    RegistrationService registrationService;

    @Override
    public void handleMessages(Update update) {
        update.hasMyChatMember();
        if (update.getMessage().isCommand()){

        }
    }
}
