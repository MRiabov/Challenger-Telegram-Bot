package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;

@Getter
public enum Buttons {
    //todo message before every challenge. Dashboard, current challenges, challenge ur friends... everything
    // todo that also means that i lack arguments in wherever this is accepted...
    //todo there should be 0 "string" in this class. One more enum "OutgoingMessages"
    //!!! messages to send

    //todo null->boolean (pair)
    //Main menu
    MAIN_MENU(
            "PLACEHOLDER",
            ButtonsMessages.MENU_MY_CHALLENGES.getText(), null,
            ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getText(), null,
            ButtonsMessages.MENU_REST.getText(), ButtonsMessages.MENU_FAQ.getText(), null
    ),
    //My challenges
    MENU_MY_CHALLENGES("PLACEHOLDER",
            ButtonsMessages.MARK_CHALLENGE_AS_COMPLETED_MENU.getText(), null,
            ButtonsMessages.SET_GOAL.getText(), ButtonsMessages.SKIP_CHALLENGE.getText(), null,
            ButtonsMessages.MAIN_MENU.getText(), null
    ),

    MARK_CHALLENGE_AS_COMPLETED("PLACEHOLDER",
            ButtonsMessages.CANCEL.getText(), null),

    SET_GOAL("PLACEHOLDER",
            ButtonsMessages.CANCEL.getText(), null),

    SKIP_CHALLENGES("CONFIRMATION PLACEHOLDER, if not just send a reason why you skip.",
            ButtonsMessages.CANCEL.getText(), null),

    //challenge your friends
    MENU_CHALLENGE_YOUR_FRIENDS("PLACEHOLDER", ,null


    ),

    CHALLENGE_DIFFICULTY_SELECTION("PLACEHOLDER",
            ),

    MENU_FAQ("PLACEHOLDER", null),

    //Commands
    UNKNOWN_COMMAND(ButtonsMessages.UNKNOWN_COMMAND.getText(), MAIN_MENU.getKeyboard()),

    //On start
    ON_START_NEW_USER(
            "Welcome, Chad. Do you need a quick review of features in this bot?",
            ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getText(),
            ButtonsMessages.ON_START_NEW_USER_FIRST_NO.getText(), null
    ),

    ON_START_NEW_USER_HELP_FIRST(
            "This bot was designed for growth through challenge.",
            ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getText(),
            ButtonsMessages.ON_START_NEW_USER_SECOND_NO.getText(), null
    ),

    ON_START_NEW_USER_HELP_SECOND(
            "HELPMESSAGE 2",
            ButtonsMessages.ON_START_NEW_USER_THIRD.getText(), null
    ),


    //!
    ;
    //!
    final private String message;
    final private String[] keyboard;

    final public static String cancelMessage = ButtonsMessages.CANCEL.getText();

    Buttons(String message, String... keyboard) {
        this.message = message;
        this.keyboard = keyboard;
    }
}
