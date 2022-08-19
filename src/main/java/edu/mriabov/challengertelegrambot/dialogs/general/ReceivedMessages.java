package edu.mriabov.challengertelegrambot.dialogs.general;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public enum ReceivedMessages {

    MENU_CHALLENGES(ButtonsMessages.MENU_CHALLENGES.getTxt(), Buttons.MY_CHALLENGES),
    ;


    final String text;
    //this is for whatever comes after this button is pressed. should ease development greatly.
    final Buttons nextInvocation;

    public static Optional<ReceivedMessages> getByText(String text){
        return Arrays.stream(of)
    }
}
