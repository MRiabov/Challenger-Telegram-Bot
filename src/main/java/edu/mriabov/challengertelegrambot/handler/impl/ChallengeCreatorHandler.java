package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChallengeCreatorHandler {

    private final ChallengeCreatorService challengeCreatorService;

    public Optional<Buttons> handleStaticMessages(long userID, String message) {
        if (message.equals(LogicButtonsMessages.CHALLENGE_YOUR_FRIENDS.getText())) {
            challengeCreatorService.fillChatPageCache(userID);
            return Optional.of(Buttons.CHAT_SELECTION);
        }
        //Nope, can't do enums with switch...
        if (message.equals(LogicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(userID, Difficulty.EASY));
        if (message.equals(LogicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(userID, Difficulty.MEDIUM));
        if (message.equals(LogicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(userID, Difficulty.DIFFICULT));
        if (message.equals(LogicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(userID, Difficulty.GOAL));

        if (message.equals(LogicButtonsMessages.FINANCES_AREA.getText()))
            return Optional.ofNullable(setArea(userID, Area.FINANCES));
        if (message.equals(LogicButtonsMessages.FITNESS_AREA.getText()))
            return Optional.ofNullable(setArea(userID, Area.FITNESS));
        if (message.equals(LogicButtonsMessages.MINDFULNESS_AREA.getText()))
            return Optional.ofNullable(setArea(userID, Area.MINDFULNESS));
        if (message.equals(LogicButtonsMessages.RELATIONSHIPS_AREA.getText()))
            return Optional.ofNullable(setArea(userID, Area.RELATIONSHIPS));

        if (message.equals(LogicButtonsMessages.CONFIRM_CHALLENGE_BILLING.getText()))
            challengeCreatorService.confirm(userID);

        return Optional.empty();
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
