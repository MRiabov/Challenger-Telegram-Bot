package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelectionButtonsMessages {

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
    //rest_period
    DURATION_2HRS(ButtonsMessages.DURATION_2HRS.getText()),
    DURATION_4HRS(ButtonsMessages.DURATION_4HRS.getText()),
    DURATION_6HRS(ButtonsMessages.DURATION_6HRS.getText()),
    DURATION_REST_DAY(ButtonsMessages.DURATION_REST_DAY.getText()),

    //!
    ;
    //!
    private final String text;

}
