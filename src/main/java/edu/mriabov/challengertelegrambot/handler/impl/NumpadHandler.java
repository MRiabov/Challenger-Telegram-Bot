package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
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

    public SendMessage handleMessages(long userID, String message) {
        if (message.substring(4).equals(Appendix.CHAT_APPENDIX.getText())) {
            if (chatPageFlip(userID, message)) return SendMessage.builder()
                    .text(Buttons.CHAT_SELECTION.getMessage())
                    .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.CHAT_APPENDIX))
                    .build();
            if (Character.isDigit(message.charAt(0))) {
                long selectedGroupID = challengeCreatorService.selectChats(userID, Character.getNumericValue(message.charAt(0)) - 1);
                if (selectedGroupID == 0)
                    return ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.INCORRECT_INPUT);
                challengeCreatorService.fillUserPageCache(userID, selectedGroupID);
                return SendMessage.builder()
                        .chatId(userID)
                        .text(Buttons.USER_SELECTION.getMessage())
                        .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.USER_APPENDIX))
                        .build();
            }
        }
        if (message.substring(4).equals(Appendix.USER_APPENDIX.getText())) {
            if (userPageFlip(userID, challengeCreatorService.getSelectedGroupID(userID), message))
                return SendMessage.builder()
                        .text(Buttons.USER_SELECTION.getMessage())
                        .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.USER_APPENDIX))
                        .build();
            if (Character.isDigit(message.charAt(0))) {
                challengeCreatorService.selectUsers(userID, userPageCache.getOnCurrentPage(userID, Character.getNumericValue(message.charAt(0) - 1)));
                return ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.DIFFICULTY_SELECTION);
            }
        }
        return ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.INCORRECT_INPUT);
    }

    private boolean chatPageFlip(long chatID, String message) {
        if (message.startsWith(ButtonsMappingUtils.previousPage)) {
            Page<Chat> page = userService.findChatsByPageable(chatID, chatPageCache.getPreviousOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }//it is okay, that here is userService, because we get chats from the list of users.
        if (message.startsWith(ButtonsMappingUtils.nextPage)) {
            Page<Chat> page = userService.findChatsByPageable(chatID, chatPageCache.getNextOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }
        return false;
    }

    private boolean userPageFlip(long userID, long groupID, String message) {
        if (message.startsWith(ButtonsMappingUtils.previousPage)) {
            Page<User> page = chatService.findUsersByPageable(userID, groupID, userPageCache.getPreviousOrLastPageable(userID));
            userPageCache.put(userID, page);
            return true;
        }
        if (message.startsWith(ButtonsMappingUtils.nextPage)) {
            Page<User> page = chatService.findUsersByPageable(userID, groupID, userPageCache.getNextOrLastPageable(userID));
            userPageCache.put(userID, page);
            return true;
        }
        return false;
    }
}
