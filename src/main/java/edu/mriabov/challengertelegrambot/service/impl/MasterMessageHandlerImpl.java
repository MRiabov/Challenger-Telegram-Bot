package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.dialogs.commands.CommandContainer;
import edu.mriabov.challengertelegrambot.service.MessageHandler;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MasterMessageHandlerImpl implements MessageHandler {

    private final SenderService senderService;
    private final ReceivedMessages receivedMessages;
    private final CommandContainer commandContainer;

    @Override
    public void handleMessages(Update update) {
        String message = update.getMessage().getText();
        switch (message.charAt(0)){
            case '\\' -> senderService.sendMessages(
                    update.getMessage().getChatId(),
                    receivedMessages.getByText(message));
            case '/' -> commandContainer.getByText(message);
        }



    }


}
