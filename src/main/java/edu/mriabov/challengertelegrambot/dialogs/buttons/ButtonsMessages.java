package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum ButtonsMessages {//package-private!

    //Meta
    MAIN_MENU("Back to main menu"),
    CANCEL("Cancel"),
    CONTINUE("Continue!"),
    //Menu
    MENU_MY_CHALLENGES("\uD83D\uDCAA My Challenges"),
    MENU_CHALLENGE_YOUR_FRIENDS("\uD83C\uDFC3\u200D♂️Challenge your friends"),
    MENU_REST("Buy some rest...\uD83D\uDCA4"),
    MENU_FAQ("❔FAQ"),

    //My challenges
    MARK_CHALLENGE_AS_COMPLETED_MENU("Mark a challenge as completed"),
    SKIP_CHALLENGE("Skip a challenge"),
    SET_GOAL("Set a goal"),
    BACK_TO_MENU("Back to menu..."),

    //Challenge your friends
    EASY_DIFFICULTY("Easy"),
    MEDIUM_DIFFICULTY("Medium"),
    DIFFICULT_DIFFICULTY("Difficult"),
    GOAL_DIFFICULTY("Goal"),
    RELATIONSHIPS_AREA("Relationships"),
    FITNESS_AREA("Fitness"),
    MINDFULNESS_AREA("Mindfulness"),
    FINANCES_AREA("Finances"),

    //Commands
    UNKNOWN_COMMAND("Error: this command doesn't seem to be supported..."),

    //Start
    ON_START_NEW_USER_FIRST_YES("Yes, guide me."),
    ON_START_NEW_USER_FIRST_NO("I'll do it myself."),

    ON_START_NEW_USER_SECOND_YES("Continue"),
    ON_START_NEW_USER_SECOND_NO("Skip"),
    ON_START_NEW_USER_THIRD("Finish")//last for now

    //!
    ;
    //!
    private final String text;


}
