package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.cache.ChallengePageCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LogicMessagesHandler {

    private final ChallengeCreatorService challengeCreatorService;
    private final ChallengePageCache challengePageCache;
    private final UserService userService;

    public Optional<Buttons> handleStaticMessages(long userID, String message) {
        Optional<LogicButtonsMessages> logicButtons =
                Arrays.stream(LogicButtonsMessages.values()).filter(logicButtonsMessages ->
                        message.equals(logicButtonsMessages.getText())).findAny();
        if (logicButtons.isEmpty()) return Optional.empty();
        switch (logicButtons.get()) {
            case CHALLENGE_YOUR_FRIENDS -> {
                challengeCreatorService.fillChatPageCache(userID);
                return Optional.of(Buttons.CHAT_SELECTION);
            }
            case EASY_DIFFICULTY -> {
                return Optional.ofNullable(setDifficulty(userID, Difficulty.EASY));
            }
            case MEDIUM_DIFFICULTY -> {
                return Optional.ofNullable(setDifficulty(userID, Difficulty.MEDIUM));
            }
            case DIFFICULT_DIFFICULTY -> {
                return Optional.ofNullable(setDifficulty(userID, Difficulty.DIFFICULT));
            }
            case GOAL_DIFFICULTY -> {
                return Optional.ofNullable(setDifficulty(userID, Difficulty.GOAL));
            }
            case FINANCES_AREA -> {
                return Optional.ofNullable(setArea(userID, Area.FINANCES));
            }
            case FITNESS_AREA -> {
                return Optional.ofNullable(setArea(userID, Area.FITNESS));
            }
            case MINDFULNESS_AREA -> {
                return Optional.ofNullable(setArea(userID, Area.MINDFULNESS));
            }
            case RELATIONSHIPS_AREA -> {
                return Optional.ofNullable(setArea(userID, Area.RELATIONSHIPS));
            }
            case CONFIRM_CHALLENGE_BILLING -> {
                return Optional.of(challengeCreatorService.confirm(userID) ? Buttons.PURCHASE_SUCCESSFUL : Buttons.NEED_MORE_COINS);
            }
            case CONFIRM_CHALLENGE_SKIP -> {
                return Optional.of(challengeCreatorService.skipChallenge(userID) ? Buttons.PURCHASE_SUCCESSFUL : Buttons.NEED_MORE_COINS);
            }
            case MARK_CHALLENGE_AS_COMPLETED -> {
                challengePageCache.put(userID, userService.findChallengesByTelegramID(userID, Pageable.ofSize(9)));
                return Optional.of(Buttons.MARK_CHALLENGE_AS_COMPLETED);
            }
            case SKIP_CHALLENGES -> {
                challengePageCache.put(userID, userService.findChallengesByTelegramID(userID, Pageable.ofSize(9)));
                return Optional.of(Buttons.SKIP_CHALLENGES);
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    public Buttons handleUsernames(long userID, String message) {
        if (challengeCreatorService.selectUsersByUsername(userID, message)) return Buttons.DIFFICULTY_SELECTION;
        else return Buttons.OTHER_USER_NOT_FOUND;
    }

    private Buttons setDifficulty(long chatID, Difficulty difficulty) {
        challengeCreatorService.selectDifficulty(chatID, difficulty);
        return Buttons.AREA_SELECTION;
    }

    private Buttons setArea(long chatID, Area area) {
        challengeCreatorService.selectArea(chatID, area);
        return Buttons.SET_DESCRIPTION;
    }

    public Buttons setMessage(long userID, String message) {
        challengeCreatorService.setDescription(userID, message);
        return Buttons.CONFIRM_SELECTION;
    }
}
