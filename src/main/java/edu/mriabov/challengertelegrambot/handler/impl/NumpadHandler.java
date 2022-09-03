package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Service
@RequiredArgsConstructor
public class NumpadHandler {

    private final ChallengeCreatorService challengeCreatorService;
    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final UserService userService;
    private final ChatService chatService;
    private final DynamicButtonsService dynamicButtonsService;


    public SendMessage handleMessages(long chatID, String message) {
        if (message.substring(4).equals(Appendix.USER_APPENDIX.getText())) {
            if (userPageFlip(chatID, message)) return SendMessage.builder()
                    .text(Buttons.USER_SELECTION.getMessage())
                    .replyMarkup(dynamicButtonsService.createMarkup(chatID, Appendix.USER_APPENDIX))
                    .build();
            if (Character.isDigit(message.charAt(0))) {
                challengeCreatorService.selectUsers(chatID,userPageCache.getOnCurrentPage(chatID, message.charAt(0)));
                userPageCache.cleanCache(chatID);
                return SendMessage.builder()
                        .text(Buttons.CHAT_SELECTION.getMessage())
                        .replyMarkup(dynamicButtonsService.createMarkup(chatID,Appendix.CHAT_APPENDIX))
                        .build();

            }
        }
        if (message.substring(4).equals(Appendix.CHAT_APPENDIX.getText())) {
            if (chatPageFlip(chatID, message)) return SendMessage.builder()
                    .text(Buttons.CHAT_SELECTION.getMessage())
                    .replyMarkup(dynamicButtonsService.createMarkup(chatID, Appendix.CHAT_APPENDIX))
                    .build();
            if (Character.isDigit(message.charAt(0))) {
                challengeCreatorService.selectChats(chatID,
                        chatPageCache.getOnCurrentPage(chatID, message.charAt(0)).getTelegramID());
                userPageCache.cleanCache(chatID);
                return ButtonsMappingUtils.buildMessageWithKeyboard(chatID, Buttons.DIFFICULTY_SELECTION);
            }
        }

        return ButtonsMappingUtils.buildMessageWithKeyboard(chatID, Buttons.INCORRECT_INPUT);
    }

    private boolean chatPageFlip(long chatID, String message) {
        if (message.startsWith("⏪")) {
            Page<Chat> page = userService.findAllByPageable(chatID, userPageCache.getPreviousOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }
        if (message.startsWith("⏩")) {
            Page<Chat> page = userService.findAllByPageable(chatID, userPageCache.getNextOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }
        return false;
    }

    private boolean userPageFlip(long chatID, String message) {
        if (message.startsWith("⏪")) {
            Page<User> page = chatService.findAllByPageable(chatID, userPageCache.getPreviousOrLastPageable(chatID));
            userPageCache.put(chatID, page);
            return true;
        }
        if (message.startsWith("⏩")) {
            Page<User> page = chatService.findAllByPageable(chatID, userPageCache.getNextOrLastPageable(chatID));
            userPageCache.put(chatID, page);
            return true;
        }
        return false;
    }
}
