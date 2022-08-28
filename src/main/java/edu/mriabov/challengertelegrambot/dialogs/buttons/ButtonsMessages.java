package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum ButtonsMessages {//package-private!

    //Meta
    MAIN_MENU("\uD83C\uDFE0Back to main menu"),
    CANCEL("❌Cancel"),
    CONTINUE("✅Continue!"),
    //Menu
    MENU_MY_CHALLENGES("\uD83D\uDCAA My Challenges"),
    MENU_CHALLENGE_YOUR_FRIENDS("\uD83C\uDFC3\u200D♂️Challenge your friends"),
    MENU_REST("Buy some rest...\uD83D\uDCA4"),
    MENU_FAQ("❔FAQ"),

    //My challenges
    MARK_CHALLENGE_AS_COMPLETED_MENU("✅Mark a challenge as completed"),
    SKIP_CHALLENGE("\uD83D\uDE34Skip a challenge"),
    SET_GOAL("\uD83C\uDFAFSet a goal"),
    BACK_TO_MENU("\uD83C\uDFE0Back to menu..."),

    //Challenge your friends
    EASY_DIFFICULTY("\uD83D\uDFE2Easy"),
    MEDIUM_DIFFICULTY("\uD83D\uDD35Medium"),
    DIFFICULT_DIFFICULTY("\uD83D\uDFE3Difficult"),
    GOAL_DIFFICULTY("\uD83C\uDFAFGoal"),
    RELATIONSHIPS_AREA("\uD83E\uDEF6Relationships"),
    FITNESS_AREA("\uD83C\uDFCB️\u200D♂️Fitness"),
    MINDFULNESS_AREA("\uD83E\uDDD8\u200D♀️Mindfulness"),
    FINANCES_AREA("\uD83D\uDCB2Finances"),

    //Commands
    UNKNOWN_COMMAND("Error: this command doesn't seem to be supported..."),

    //Start
    ON_START_NEW_USER_FIRST_YES("\uD83D\uDC41Yes, guide me."),
    ON_START_NEW_USER_FIRST_NO("⏩I'll do it myself."),

    ON_START_NEW_USER_SECOND_YES("✅Continue"),
    ON_START_NEW_USER_SECOND_NO("⏩Skip"),
    ON_START_NEW_USER_THIRD("\uD83C\uDD97Finish")//last for now

    //!
    ;
    //!
    private final String text;


}
