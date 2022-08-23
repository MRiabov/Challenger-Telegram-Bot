package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Slf4j
public enum ReceivedMessages {
    //here you receive messages, and, based on the answer, decide what to do next
    //= messages to catch and decide the next action
    MENU_CHALLENGES(ButtonsMessages.MENU_CHALLENGES.getTxt(), Buttons.MY_CHALLENGES),
    MAIN_MENU(ButtonsMessages.MAIN_MENU.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_YES(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_NO(ButtonsMessages.ON_START_NEW_USER_FIRST_NO.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_FIRST_YES(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_SECOND),
    ON_START_NEW_USER_FIRST_NO(ButtonsMessages.ON_START_NEW_USER_SECOND_NO.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_SECOND_FINISH(ButtonsMessages.ON_START_NEW_USER_THIRD.getTxt(), Buttons.MAIN_MENU)

    //!
    ;
    //!

    private final String receivedMessage;
    //this is for whatever comes after this button is pressed. should ease development greatly.
    private final Buttons nextInvocation;

    public static Optional<ReceivedMessages> getByText(String text) {
        return Arrays.stream(values())
                .filter(receivedMessages -> receivedMessages.receivedMessage.equals(text))
                .findFirst();
    }

}
