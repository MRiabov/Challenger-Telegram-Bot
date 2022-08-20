package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public enum ReceivedMessages {
//here you receive messages, and decide what to do next
    MENU_CHALLENGES(ButtonsMessages.MENU_CHALLENGES.getTxt(), Buttons.MY_CHALLENGES),

    ON_START_NEW_USER_YES(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_NO(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_FIRST_YES(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_FIRST_NO(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(), Buttons.MAIN_MENU),

   ON_START_NEW_USER_SECOND_FINISH(ButtonsMessages.ON_START_NEW_USER_THIRD.getTxt(), Buttons.MAIN_MENU)

    //!
    ;
    //!
    final String text;
    //this is for whatever comes after this button is pressed. should ease development greatly.
    final Buttons nextInvocation;

    public static Optional<ReceivedMessages> getByText(String text){
        return Arrays.stream(values())
                .filter(receivedMessages -> receivedMessages.text.equals(text))
                .findFirst();
    }
}
