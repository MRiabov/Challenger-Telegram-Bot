package edu.mriabov.challengertelegrambot.service.impl;

import com.vdurmont.emoji.EmojiManager;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.dialogs.commands.CommandContainer;
import edu.mriabov.challengertelegrambot.service.MessageHandler;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterMessageHandlerImpl implements MessageHandler {

    private final SenderService senderService;
    private final ReceivedMessagesContainer receivedMessagesContainer;
    private final CommandContainer commandContainer;
    private final LogicMessageHandler logicMessageHandler;

    @Override
    public void handleMessages(Update update) {
        String message = update.getMessage().getText();
        log.info("Successfully received the message to the handler: " + message);
        if (EmojiManager.containsEmoji(message.substring(0, 3)) || TelegramUtils.checkForUnsupportedEmoji(message)) { //is a button
            Optional<Buttons> logicButtons = logicMessageHandler.handleMessages(update);
            if (logicButtons.isPresent())
                senderService.sendMessages(update.getMessage().getChatId(), logicButtons.get());
            else
                senderService.sendMessages(update.getMessage().getChatId(), receivedMessagesContainer.getByText(message));
        } else {
            if (message.charAt(0) == '/') commandContainer.executeByText(message, update);
        }
    }
}
