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
        long id = update.getMessage().getChatId();
        log.info("Successfully received the message to the handler: " + message);
        if (EmojiManager.containsEmoji(message.substring(0, 3)) || TelegramUtils.checkForUnsupportedEmoji(message)) { //is a button
            buttonsHandler(update, message);
        } else if (message.charAt(0) == '/') commandContainer.executeByText(update);
        else if (message.startsWith("@")) senderService.sendMessages(id,
                logicMessageHandler.handleUsernames(update));
    }

    private void buttonsHandler(Update update, String message) {
        long id = update.getMessage().getChatId();
        Optional<Buttons> logicButtons = logicMessageHandler.handleStaticMessages(update);
        if (logicButtons.isPresent()) senderService.sendMessages(id, logicButtons.get());
        else senderService.sendMessages(id, receivedMessagesContainer.getByText(message));
    }
}
