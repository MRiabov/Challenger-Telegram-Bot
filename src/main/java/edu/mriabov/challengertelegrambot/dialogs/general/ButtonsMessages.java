package edu.mriabov.challengertelegrambot.dialogs.general;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ButtonsMessages {
    //Menu
    MENU_CHALLENGES("My Challenges"),
    MENU_CHALLENGE_YOUR_FRIENDS("Challenge your friends"),
    MENU_REST("Buy some rest..."),
    MENU_FAQ("FAQ"),


    //Commands
    UNKNOWN_COMMAND("Error: this command doesn't seem to be supported..."),

    //Start
    ON_START_NEW_USER_FIRST_YES("Yes, guide me."),
    ON_START_NEW_USER_FIRST_NO("I'll do it myself."),

    ON_START_NEW_USER_SECOND_YES("Continue"),
    ON_START_NEW_USER_SECOND_NO("Skip"),
    ON_START_NEW_USER_THIRD("Finish")//last for now


;
    private final String txt;
}
