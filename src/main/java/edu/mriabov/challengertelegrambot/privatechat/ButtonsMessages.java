package edu.mriabov.challengertelegrambot.privatechat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum ButtonsMessages {//package-private!

    //Meta
    MAIN_MENU("\uD83C\uDFE0Back to main menu"),
    BACK_TO_MENU("\uD83C\uDFE0Back to menu..."),
    CANCEL("❌Cancel"),
    CONTINUE("✅Continue!"),
    CONTINUE_WORKING("\uD83D\uDE16 Continue working..."),
    COMPLETE_FAILED_CHALLENGE("✅I've completed it now."),

    //Menu
    MENU_MY_CHALLENGES("\uD83D\uDCAA My Challenges"),
    MENU_CHALLENGE_YOUR_FRIENDS("\uD83C\uDFC3\u200D♂️Challenge your friends"),
    MENU_REST("\uD83D\uDCA4Buy some rest..."),
    MENU_FAQ("❔FAQ"),

    //My challenges
    MARK_CHALLENGE_AS_COMPLETED("✅Mark a challenge as completed"),
    SKIP_CHALLENGE("\uD83D\uDE34Skip a challenge"),
    SET_GOAL("\uD83C\uDFAFSet a goal"),
    CONFIRM_CHALLENGE_SKIP("\uD83D\uDC8EConfirm challenge skip\uD83D\uDC8E"),

    //Challenge your friends
    EASY_DIFFICULTY("\uD83D\uDFE2Easy"),
    MEDIUM_DIFFICULTY("\uD83D\uDD35Medium"),
    DIFFICULT_DIFFICULTY("\uD83D\uDFE3Difficult"),
    GOAL_DIFFICULTY("\uD83C\uDFAFGoal"),
    RELATIONSHIPS_AREA("\uD83E\uDEF6Relationships"),
    FITNESS_AREA("\uD83C\uDFCB️\u200D♂️Fitness"),
    MINDFULNESS_AREA("\uD83E\uDDD8\u200D♀️Mindfulness"),
    FINANCES_AREA("\uD83D\uDCB2Finances"),
    CONFIRM_CHALLENGE_BILLING("\uD83D\uDC8EConfirm billing\uD83D\uDC8E"),

    //Buy some rest...
    REST("\uD83E\uDD24I'm just tired..."),
    ADDICTION("\uD83D\uDC8A\uD83C\uDFAEFor addiction\uD83E\uDD43"),
    PROCEED_TO_REST("⏩Proceed to rest..."),

    DURATION_2HRS("\uD83D\uDD50 2 Hours"),
    DURATION_4HRS("\uD83D\uDD53 4 Hours"),
    DURATION_6HRS("\uD83D\uDD55 6 Hours"),
    DURATION_REST_DAY("⏳ Rest Day"),

    //pagination appendixes
    USER_APPENDIX("user"),
    CHAT_APPENDIX("chat"),

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
