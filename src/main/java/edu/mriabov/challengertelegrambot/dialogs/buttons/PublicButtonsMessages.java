package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PublicButtonsMessages {
    //misc
    CANCEL(ButtonsMessages.CANCEL.getText()),
    //difficulties
    EASY_DIFFICULTY(ButtonsMessages.EASY_DIFFICULTY.getText()),
    MEDIUM_DIFFICULTY(ButtonsMessages.MEDIUM_DIFFICULTY.getText()),
    DIFFICULT_DIFFICULTY(ButtonsMessages.DIFFICULT_DIFFICULTY.getText()),
    GOAL_DIFFICULTY(ButtonsMessages.GOAL_DIFFICULTY.getText());

    private final String text;

}
