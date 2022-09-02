package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.LogicButtonsMessages;
import edu.mriabov.challengertelegrambot.service.ChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeCreatorHandler {

    private final ChallengeCreatorService challengeCreatorService;

    public Optional<Buttons> handleStaticMessages(long chatID,String message) {
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

        if (message.startsWith("️⃣", 1)) return Optional.of(dynamicButtonsHandler(message,
                chatID, Integer.parseInt(String.valueOf(message.charAt(0)))));

        //handle message here. then it goes to

        return Optional.empty();
    }

    public Buttons handleUsernames(long id,String message) {
        challengeCreatorService.selectUsersByUsername(id,message);
        return Buttons.DIFFICULTY_SELECTION;
    }

    private Buttons dynamicButtonsHandler(String message, long chatID, int selectedNumber) {
        if (message.substring(4).equals(Appendix.USER_APPENDIX.getText()))
            return selectChats(chatID, selectedNumber);
        //todo same, but with username
        if (message.substring(4).equals(Appendix.CHAT_APPENDIX.getText()))
            return selectUsers(chatID, selectedNumber);
        return Buttons.INCORRECT_INPUT;
    }

    private Buttons selectChats(long chatID, int selectedNumber) {
        challengeCreatorService.selectChats(chatID,selectedNumber);
        return Buttons.USER_SELECTION;
    }

    private Buttons selectUsers(long chatID, int selectedNumber) {

        challengeCreatorService.selectUsers(chatID,selectedNumber);
        return Buttons.DIFFICULTY_SELECTION;
    }

    private Buttons setDifficulty(long chatID, Difficulty difficulty) {
        challengeCreatorService.selectDifficulty(chatID, difficulty);
        return Buttons.AREA_SELECTION;
    }

    private Buttons setArea(long chatID, Area area) {
        challengeCreatorService.selectArea(chatID, area);
        return Buttons.AREA_SELECTION;
    }
}
