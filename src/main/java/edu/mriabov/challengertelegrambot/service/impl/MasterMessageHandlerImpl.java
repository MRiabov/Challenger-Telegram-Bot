package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.dialogs.commands.CommandContainer;
import edu.mriabov.challengertelegrambot.service.MessageHandler;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterMessageHandlerImpl implements MessageHandler {

    private final SenderService senderService;
    ReceivedMessagesContainer receivedMessagesContainer;
    private final CommandContainer commandContainer;

    @Override
    public void handleMessages(Update update) {
        log.debug("Successfully received the message to the handler");
        String message = update.getMessage().getText();
        switch (message.charAt(0)){
            case '\\' -> senderService.sendMessages(update.getMessage().getChatId(),receivedMessagesContainer.getByText(message));
            case '/' -> commandContainer.executeByText(message,update);

        }



    }


}
