package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;

@Getter
public enum Buttons {
    //todo message before every challenge. Dashboard, current challenges, challenge ur friends... everything
    // todo that also means that i lack arguments in wherever this is accepted...
    //todo there should be 0 "string" in this class. One more enum "OutgoingMessages"
    //!!! messages to send

    //todo null->boolean (pair)
    MAIN_MENU(
            "PLACEHOLDER",
            ButtonsMessages.MENU_CHALLENGES.getTxt(), null,
            ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getTxt(), null,
            ButtonsMessages.MENU_REST.getTxt(), ButtonsMessages.MENU_FAQ.getTxt(), null
    ),

    MY_CHALLENGES("PLACEHOLDER",null),
    CHALLENGE_YOUR_FRIENDS("PLACEHOLDER",null),
    FAQ("PLACEHOLDER",null),
    //Commands
    UNKNOWN_COMMAND(ButtonsMessages.UNKNOWN_COMMAND.getTxt(), MAIN_MENU.getKeyboard()),

    //On start
    ON_START_NEW_USER(
            "Welcome, Chad. Do you need a quick review of features in this bot?",
            ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(),
            ButtonsMessages.ON_START_NEW_USER_FIRST_NO.getTxt(), null
    ),

    ON_START_NEW_USER_HELP_FIRST(
            "This bot was designed for growth through challenge.",
            ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(),
            ButtonsMessages.ON_START_NEW_USER_SECOND_NO.getTxt(), null
    ),

    ON_START_NEW_USER_HELP_SECOND(
            "HELPMESSAGE 2",
            ButtonsMessages.ON_START_NEW_USER_THIRD.getTxt(),null
    ),



    //!
    ;
    //!
    final private String message;
    final private String[] keyboard;

    Buttons(String message, String ... keyboard) {
        this.message = message;
        this.keyboard = keyboard;
    }
}
