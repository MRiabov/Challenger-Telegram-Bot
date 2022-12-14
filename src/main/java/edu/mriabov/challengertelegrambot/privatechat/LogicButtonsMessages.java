package edu.mriabov.challengertelegrambot.privatechat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogicButtonsMessages {

    //difficulties
    EASY_DIFFICULTY(ButtonsMessages.EASY_DIFFICULTY.getText()),
    MEDIUM_DIFFICULTY(ButtonsMessages.MEDIUM_DIFFICULTY.getText()),
    DIFFICULT_DIFFICULTY(ButtonsMessages.DIFFICULT_DIFFICULTY.getText()),
    GOAL_DIFFICULTY(ButtonsMessages.GOAL_DIFFICULTY.getText()),

    //areas
    FINANCES_AREA(ButtonsMessages.FINANCES_AREA.getText()),
    RELATIONSHIPS_AREA(ButtonsMessages.RELATIONSHIPS_AREA.getText()),
    MINDFULNESS_AREA(ButtonsMessages.MINDFULNESS_AREA.getText()),
    FITNESS_AREA(ButtonsMessages.FITNESS_AREA.getText()),

    CHALLENGE_YOUR_FRIENDS(ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getText()),
    CONFIRM_CHALLENGE_BILLING(ButtonsMessages.CONFIRM_CHALLENGE_BILLING.getText()),
    MARK_CHALLENGE_AS_COMPLETED(ButtonsMessages.MARK_CHALLENGE_AS_COMPLETED.getText()),
    SKIP_CHALLENGES(ButtonsMessages.SKIP_CHALLENGE.getText()),
    CONFIRM_CHALLENGE_SKIP(ButtonsMessages.CONFIRM_CHALLENGE_SKIP.getText()),
    //!
    ;
    //!

    private final String text;
}
