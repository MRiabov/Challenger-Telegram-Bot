package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.commands.CommandContainer;
import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupMasterMessageHandler implements MessageHandler {
    private final CommandContainer commandContainer;

    @Override
    public void handleMessages(Update update) {
        if (update.getMessage().isCommand()) {
            log.info("Received a command from chat " + update.getMessage().getChat().getTitle() +
                    " with text " + update.getMessage().getText());
            commandContainer.executeByText(update.getMessage());
        }
    }
}
