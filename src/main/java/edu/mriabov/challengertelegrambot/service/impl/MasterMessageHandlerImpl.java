package edu.mriabov.challengertelegrambot.service.impl;

import com.vdurmont.emoji.EmojiManager;
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
    private final ReceivedMessagesContainer receivedMessagesContainer;
    private final CommandContainer commandContainer;

    @Override
    public void handleMessages(Update update) {
        String message =update.getMessage().getText();
        log.info("Successfully received the message to the handler: "+message);
        if (EmojiManager.containsEmoji(message.substring(0,3))) { //is a button

            senderService.sendMessages(update.getMessage().getChatId(), receivedMessagesContainer.getByText(message));
        } else {
            if (message.charAt(0) == '/') commandContainer.executeByText(message, update);
        }



    }


}
