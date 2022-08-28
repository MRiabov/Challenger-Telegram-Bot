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
            "Welcome, " + ButtonsMessages.MAIN_MENU.getText() +//getByIdOrDefault
                    "\n\n Your stats:" +
                    "\nFinance: " +
                    "\nRelationships: " +
                    "\nFitness: " +
                    "\nMindfulness: " +
                    "\nYour current coin count: " +
                    "\nChallenges to be completed: " +
                    "\n\nPICK A RANDOM QUOTE",
            ButtonsMessages.MENU_MY_CHALLENGES.getText(), null,
            ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getText(), null,
            ButtonsMessages.MENU_REST.getText(), ButtonsMessages.MENU_FAQ.getText(), null
    ),
    //My challenges
    MENU_MY_CHALLENGES("PLACEHOLDER",
            ButtonsMessages.MARK_CHALLENGE_AS_COMPLETED_MENU.getText(), null,
            ButtonsMessages.SET_GOAL_DESCRIPTION.getText(), ButtonsMessages.SKIP_CHALLENGE.getText(), null,
            ButtonsMessages.MAIN_MENU.getText(), null
    ),

    MARK_CHALLENGE_AS_COMPLETED("Well done for completing a challenge!" +
            "\n If you would like to complete a challenge, enter an ID next to it." +
            "LIST OF CHALLENGES",
            ButtonsMessages.CANCEL.getText(), null),

    SET_GOAL_DESCRIPTION("Goals is a burning desire which you think of each time you wake up" +
            "\n Set a goal's description...",
            ButtonsMessages.CANCEL.getText(), null),

    SKIP_CHALLENGES("CONFIRMATION PLACEHOLDER, if not just send a reason why you skip.",
            ButtonsMessages.CANCEL.getText(), null),

    //challenge your friends

    MENU_CHALLENGE_YOUR_FRIENDS("""
            Select a difficulty.
                        
             Difficulty can be of 3 types:
             1. Easy - <5 minutes. A short, simple task, like doing 10 pushups or calling your family. Probably routine.
             2. Medium - <20 minutes. Longer, perhaps more difficult task. Meditation, talking to that one girl, etc.
             3. Difficult - <60 minutes. Reading a good book, learning a language, going to the gym.
             4. Goal - set a very exact goal: gain +3kg in muscle growth, learn a skill, outline a business idea in detail.\s""",

            ButtonsMessages.EASY_DIFFICULTY.getText(),ButtonsMessages.MEDIUM_DIFFICULTY.getText(),null,
            ButtonsMessages.DIFFICULT_DIFFICULTY.getText(),ButtonsMessages.GOAL_DIFFICULTY.getText(),null,
            ButtonsMessages.CANCEL.getText(), null
    ),

    AREA_SELECTION("Select, in which area is this goal allocated.",
            ButtonsMessages.RELATIONSHIPS_AREA.getText(), ButtonsMessages.FINANCES_AREA.getText(), null,
            ButtonsMessages.FITNESS_AREA.getText(), ButtonsMessages.MINDFULNESS_AREA.getText(), null,
            ButtonsMessages.CANCEL.getText(), null
    ),

    SELECT_CHAT("Input the ID chat you would like to send:" +
            "chatlist here", "INPUT FUNCTION HERE",null     //todo chatList here
    ),

    ONLY_ONE_CHAT("You have only one chat, and it has been selected.",
            ButtonsMessages.CONTINUE.getText(),null),

    SELECT_USER("Input the ID of the Gigachad you would like to challenge",
            ButtonsMessages.CANCEL.getText(), null),

    //buy some rest...

    IS_ADDICTION("""
            Is your rest caused by an addiction?
            Addictions ALSO include:
            
            Pornography,
            Gaming,
            Binge watching YouTube, Netflix, etc,
            Social media.
            """, ButtonsMessages.ADDICTION.getText(),ButtonsMessages.REST.getText(),null),

    DURATION_OF_THE_REST("For how long are you planning to rest?",
            ButtonsMessages.DURATION_2HRS.getText(),ButtonsMessages.DURATION_4HRS.getText(),null,
            ButtonsMessages.DURATION_6HRS.getText(),ButtonsMessages.DURATION_REST_DAY.getText(),null),


    //misc

    NEED_MORE_COINS("Hey! This action requires more coins then you have!",ButtonsMessages.CONTINUE_WORKING.getText()),

    MENU_FAQ("""
             We don’t grow when things are easy; we grow when we face challenges.
             This bot was created with this idea in mind. The core idea is to better one's finances, physique, and other *self*-improvement through the hardship and willpower, while becoming mentally stable, kind, and good person.
             So let's become the strong men this world desires!
            """, null),

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
