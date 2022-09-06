package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCreatorHandler {

    private final ChallengeCreatorService challengeCreatorService;

    public Optional<Buttons> handleStaticMessages(long chatID, String message) {
        if (message.equals(LogicButtonsMessages.CHALLENGE_YOUR_FRIENDS.getText())) {
            challengeCreatorService.fillChatPageCache(chatID);
            return Optional.of(Buttons.CHAT_SELECTION);
        }
        //Nope, can't do enums with switch...
        if (message.equals(LogicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.EASY));
        if (message.equals(LogicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.MEDIUM));
        if (message.equals(LogicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.DIFFICULT));
        if (message.equals(LogicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Optional.ofNullable(setDifficulty(chatID, Difficulty.GOAL));

        if (message.equals(LogicButtonsMessages.FINANCES_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.FINANCES));
        if (message.equals(LogicButtonsMessages.FITNESS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.FITNESS));
        if (message.equals(LogicButtonsMessages.MINDFULNESS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.MINDFULNESS));
        if (message.equals(LogicButtonsMessages.RELATIONSHIPS_AREA.getText()))
            return Optional.ofNullable(setArea(chatID, Area.RELATIONSHIPS));

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
        return Buttons.CONFIRM_SELECTION;
    }
}
