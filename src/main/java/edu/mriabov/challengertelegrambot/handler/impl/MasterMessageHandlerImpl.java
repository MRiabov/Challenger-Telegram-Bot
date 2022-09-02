package edu.mriabov.challengertelegrambot.handler.impl;

import com.vdurmont.emoji.EmojiManager;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.dialogs.commands.CommandContainer;
import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
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
    private final ChallengeCreatorHandler challengeCreatorHandler;
    private final UserService userService;

    @Override
    public void handleMessages(Update update) {
        String message = update.getMessage().getText();
        long chatID = update.getMessage().getChatId();
        log.info("Successfully received the message to the handler: " + message);
        if (message.charAt(0) == '/') commandContainer.executeByText(update);//if the message is a command
        else if (EmojiManager.containsEmoji(message.substring(0, 3))
                || TelegramUtils.checkForUnsupportedEmoji(message)) {//if the message is a button
            buttonsHandler(chatID, message);
        } else if (message.startsWith("@")) senderService.sendMessages(chatID,//if the message is a username
                challengeCreatorHandler.handleUsernames(chatID, message));
    }

    private void buttonsHandler(long chatID, String message) {
        if (!userService.existsByTelegramId(chatID)) senderService.sendMessages(chatID, Buttons.USER_NOT_FOUND);
        Optional<Buttons> logicButtons = challengeCreatorHandler.handleStaticMessages(chatID, message);
        if (logicButtons.isPresent()) senderService.sendMessages(chatID, logicButtons.get());
        else senderService.sendMessages(chatID, receivedMessagesContainer.getByText(message));
    }
}