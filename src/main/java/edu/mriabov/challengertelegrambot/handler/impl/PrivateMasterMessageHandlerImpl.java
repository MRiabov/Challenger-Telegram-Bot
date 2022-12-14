package edu.mriabov.challengertelegrambot.handler.impl;

import com.vdurmont.emoji.EmojiManager;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PrivateMasterMessageHandlerImpl implements MessageHandler {

    private final SenderService senderService;
    private final ReceivedMessagesContainer receivedMessagesContainer;
    private final LogicMessagesHandler logicMessagesHandler;
    private final UserService userService;
    private final NumpadHandler numpadHandler;

    @Override
    public void handleMessages(Update update) {
        String message = update.getMessage().getText();
        long userID = update.getMessage().getChatId();
        log.info("Successfully received the message to the PM handler: " + message);
        if (update.getMessage().isCommand()) log.info("Attempted to use a command!");
        else if (EmojiManager.containsEmoji(message.substring(0, 3))
                || TelegramUtils.checkForUnsupportedEmoji(message)) {//if the message is a button
            buttonsHandler(userID, message);
        } else if (message.startsWith("@")) senderService.sendMessages(userID,//if the message is a username
                logicMessagesHandler.handleUsernames(userID, message));
        else if (message.length()>=40) senderService.sendMessages(userID, logicMessagesHandler.setMessage(userID,message));
    }

    private void buttonsHandler(long chatID, String message) {
        if (!userService.existsByTelegramId(chatID)) {
            senderService.sendMessages(chatID, Buttons.USER_NOT_FOUND);
            return;
        }
        Optional<Buttons> logicButtons = logicMessagesHandler.handleStaticMessages(chatID, message);
        if (logicButtons.isPresent()) senderService.sendMessages(chatID, logicButtons.get());
        else if (message.startsWith("??????",1)||message.startsWith(ButtonsMappingUtils.PREVIOUS_PAGE)||message.startsWith(ButtonsMappingUtils.NEXT_PAGE))
            senderService.sendMessages(numpadHandler.handleMessages(chatID, message));
        else senderService.sendMessages(chatID, receivedMessagesContainer.getByText(message));
    }
}
