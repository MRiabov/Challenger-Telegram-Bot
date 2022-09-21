package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.cache.ChallengePageCache;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NumpadHandler {

    private final ChallengeCreatorService challengeCreatorService;
    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final UserService userService;
    private final GroupService groupService;
    private final ChallengePageCache challengePageCache;
    private final DynamicButtonsService dynamicButtonsService;

    public SendMessage handleMessages(long userID, String message) {
        Optional<SendMessage> sendMessage = switch (Appendix.valueOf((message.substring(4) + "_appendix").toUpperCase())) {
            case USER_APPENDIX -> userAppendix(userID, message);
            case CHAT_APPENDIX -> chatAppendix(userID, message);
            case CHALLENGE_APPENDIX -> challengeAppendix(userID, message);
            case WEEKS_APPENDIX -> weeksAppendix(userID,message);
            case SKIP_APPENDIX -> new SendMessage();
        };
        return ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.INCORRECT_INPUT);
    }

    private boolean chatPageFlip(long chatID, String message) {
        if (message.startsWith(ButtonsMappingUtils.previousPage)) {
            Page<Group> page = userService.findChatsByPageable(chatID, chatPageCache.getPreviousOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }//it is okay, that here is userService, because we get chats from the list of users.
        if (message.startsWith(ButtonsMappingUtils.nextPage)) {
            Page<Group> page = userService.findChatsByPageable(chatID, chatPageCache.getNextOrLastPageable(chatID));
            chatPageCache.put(chatID, page);
            return true;
        }
        return false;
    }

    private boolean userPageFlip(long userID, long groupID, String message) {
        if (message.startsWith(ButtonsMappingUtils.previousPage)) {
            Page<User> page = groupService.findUsersByPageable(userID, groupID, userPageCache.getPreviousOrLastPageable(userID));
            userPageCache.put(userID, page);
            return true;
        }
        if (message.startsWith(ButtonsMappingUtils.nextPage)) {
            Page<User> page = groupService.findUsersByPageable(userID, groupID, userPageCache.getNextOrLastPageable(userID));
            userPageCache.put(userID, page);
            return true;
        }
        return false;
    }

    private Optional<SendMessage> chatAppendix(long userID, String message) {
        if (chatPageFlip(userID, message)) return Optional.ofNullable(SendMessage.builder()
                .text(Buttons.CHAT_SELECTION.getMessage())
                .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.CHAT_APPENDIX))
                .build());
        if (Character.isDigit(message.charAt(0))) {
            Optional<Group> selectedGroup = challengeCreatorService.selectChats(userID, Character.getNumericValue(message.charAt(0)) - 1);
            if (selectedGroup.isEmpty())
                return Optional.of(ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.INCORRECT_INPUT));
            challengeCreatorService.fillUserPageCache(userID, selectedGroup.get());
            return Optional.ofNullable(SendMessage.builder()
                    .chatId(userID)
                    .text(Buttons.USER_SELECTION.getMessage())
                    .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.USER_APPENDIX))
                    .build());
        }
        return Optional.empty();
    }

    private Optional<SendMessage> userAppendix(long userID, String message) {
        if (userPageFlip(userID, challengeCreatorService.getSelectedGroupID(userID).getTelegramId(), message))
            return Optional.ofNullable(SendMessage.builder()
                    .text(Buttons.USER_SELECTION.getMessage())
                    .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.USER_APPENDIX))
                    .build());
        if (Character.isDigit(message.charAt(0))) {
            challengeCreatorService.selectUsers(userID, userPageCache.getOnCurrentPage(userID, Character.getNumericValue(message.charAt(0)) - 1));
            return Optional.of(ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.DIFFICULTY_SELECTION));
        }
        return Optional.empty();
    }

    private Optional<SendMessage> challengeAppendix(long userID, String message) {
        if (challengePageFlip(userID, message)) return Optional.ofNullable(SendMessage.builder()
                .text(Buttons.USER_SELECTION.getMessage())
                .replyMarkup(dynamicButtonsService.createMarkup(userID, Appendix.USER_APPENDIX))
                .build());
        if (Character.isDigit(message.charAt(0))) {
            userService.completeChallenge(userID, challengePageCache.getOnCurrentPage(userID, Character.getNumericValue(message.charAt(0)) - 1));
            return Optional.of(ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.MARK_CHALLENGE_AS_COMPLETED));
        }
        return Optional.empty();
    }

    private boolean challengePageFlip(long userID, String message) {
        if (message.startsWith(ButtonsMappingUtils.previousPage)) {
            Page<Challenge> page = userService.findChallengesByTelegramID(userID, challengePageCache.getPreviousOrLastPageable(userID));
            challengePageCache.put(userID, page);
            return true;
        }
        if (message.startsWith(ButtonsMappingUtils.nextPage)) {
            Page<Challenge> page = userService.findChallengesByTelegramID(userID, challengePageCache.getNextOrLastPageable(userID));
            challengePageCache.put(userID, page);
            return true;
        }
        return false;
    }

    private Optional<SendMessage> weeksAppendix(long userID, String message){
        if (Character.isDigit(message.charAt(0))) {
            challengeCreatorService.selectGoalLength(userID, message.charAt(0));
            return Optional.of(ButtonsMappingUtils.buildMessageWithKeyboard(userID, Buttons.AREA_SELECTION));
        }
        return Optional.empty();
    }
}
