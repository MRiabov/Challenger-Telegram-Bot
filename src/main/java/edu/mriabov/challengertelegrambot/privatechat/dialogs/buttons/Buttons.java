package edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons;

import lombok.Getter;

@Getter
public enum Buttons {

    //!!! messages to send
    //Main menu
    MAIN_MENU(
            """
                    Your stats:
                    💰Finance: _%2$d_
                    🫶Relationships: _%3$d_
                    💪Fitness: _%4$d_
                    🧘Mindfulness: _%5$d_
                    Your current coin count: _%6$d_💎
                    *Challenges to be completed:*

                    PICK A RANDOM QUOTE""",
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
    CHAT_SELECTION("""
            Input the ID group you would like to send:
            %7$s
            You can also just @ the user you would like to include, the system will find him automatically\\."""
    ),

    ONLY_ONE_CHAT("You have only one group, and it has been selected\\.",
            ButtonsMessages.CONTINUE.getText(), null),

    USER_SELECTION("""
            Input the ID or of the Gigachad you would like to challenge
            %8$s
            Alternatively, you can just send his @"""),

    OTHER_USER_NOT_FOUND("""
            Username not found *in your groups*\\.
            Try to look it up manually\\.
            """, ButtonsMessages.MAIN_MENU.getText(), null),

    DIFFICULTY_SELECTION("""
            Select a difficulty\\.
            Difficulty can be of 3 types:
            1\\. Easy \\- <5 minutes\\. A short, simple task, like doing 10 pushups or calling your family\\. Probably routine\\.
            2\\. Medium \\- <20 minutes\\. Longer, perhaps more difficult task\\. Meditation, talking to that one girl, etc\\.
            3\\. Difficult \\- <60 minutes\\. Reading a good book, learning a language, going to the gym\\.
            3\\. Difficult \\- <60 minutes\\. Reading a good book, learning a language, going to the gym\\.
            4\\. Goal \\- set a very exact goal: gain +3kg in muscle growth, learn a skill, outline a business idea in detail.\s""",

            ButtonsMessages.EASY_DIFFICULTY.getText(), ButtonsMessages.MEDIUM_DIFFICULTY.getText(), null,
            ButtonsMessages.DIFFICULT_DIFFICULTY.getText(), ButtonsMessages.GOAL_DIFFICULTY.getText(), null,
            ButtonsMessages.CANCEL.getText(), null
    ),

    AREA_SELECTION("Select, in which area is this goal allocated\\.",
            ButtonsMessages.RELATIONSHIPS_AREA.getText(), ButtonsMessages.FINANCES_AREA.getText(), null,
            ButtonsMessages.FITNESS_AREA.getText(), ButtonsMessages.MINDFULNESS_AREA.getText(), null,
            ButtonsMessages.CANCEL.getText(), null
    ),

    SET_DESCRIPTION("""
            Now, write a description for a challenge\\. A clear, understandable message\\.
            I would like to remind, that these challenges meant to be purely for better\\. Don't write anything, which will intentionally harm one's life, relations or other parts of one's life\\.
            These challenges are meant for growth, and not destruction\\.
            *Minimal length \\- 40 symbols\\.*""",
            ButtonsMessages.CANCEL.getText(), null),

    CONFIRM_SELECTION("""
            So, this is your challenge\\.
            %9$s
            Is everything right?
            """,
            ButtonsMessages.CONFIRM_CHALLENGE_BILLING.getText(), null,
            ButtonsMessages.CANCEL.getText(), null),

    //buy some rest...

    IS_ADDICTION("""
            Is your rest caused by an addiction?
            Addictions ALSO include:
            
            Pornography,
            Gaming,
            Binge watching YouTube, Netflix, etc,
            Social media.
            """, ButtonsMessages.ADDICTION.getText(), ButtonsMessages.REST.getText(), null),

    ADDICTION_HELP("""
            I don't know what your addiction is, but it has to be treated. It is good, that you have stepped on a path of self-improvement,
            BUT!
            Addiction drags your self-image down. Each time you look in the mirror, you probably think:
            "Damn, how did I come to all of this? I don't even have that much fun from it, yet I still keep doing it.."
            And there is no thing more important than mental health and self-image, because it affects ALL your other areas of life. How can others treat you with respect if you yourself don't?
            There is a great book, "Atomic Habits"(+100k 5-star ratings on Amazon). It teaches how to avoid bad habits, as well as creating new ones. Highly recommend to read.
            I have quit all the "modern" addictions myself. The worst part about being a gamer, or a porn addict is that you don't even realise it's bad.
            And it's terribly bad.
            If you are a gamer, ask yourself, "Why compete in games, when I can compete in real life?"
            If you are a porn addict, ask yourself, "Why masturbate on women, when you can workout in the same time, improve your looks and just get women?"
            Addiction is a choice, you can either choose to get instant gratification, or you can do something meaningful in the same time.
            """, ButtonsMessages.PROCEED_TO_REST.getText(), null),

    DURATION_OF_THE_REST("For how long are you planning to rest?",
            ButtonsMessages.DURATION_2HRS.getText(), ButtonsMessages.DURATION_4HRS.getText(), null,
            ButtonsMessages.DURATION_6HRS.getText(), ButtonsMessages.DURATION_REST_DAY.getText(), null),

    BOUGHT_REST("""
                Fine, take your rest, you've earned it.
                But remember, that you are in a constant race, and when you do nothing, you everyone else moves forward.
                I suggest you watch a video "Stop letting other men pipe your wife" by Hamza in the meantime.
            """, ButtonsMessages.BACK_TO_MENU.getText(), null),

    //misc
    NEED_MORE_COINS("Hey! This action requires more coins then you have!", ButtonsMessages.CONTINUE_WORKING.getText()),

    PURCHASE_SUCCESSFUL("""
            Purchase successful!
            Your remaining coin count: %6
            """, ButtonsMessages.BACK_TO_MENU.getText()),

    MENU_FAQ("""
            We don’t grow when things are easy; we grow when we face challenges.
            This bot was created with this idea in mind. The core idea is to better one's finances, physique, and other *self*-improvement through the hardship and willpower, while becoming mentally stable, kind, and good person.
            So let's become the strong men this world desires!
            """, ButtonsMessages.BACK_TO_MENU.getText()),

    //Commands
    UNKNOWN_COMMAND("Error: this command doesn't seem to be supported...", ButtonsMessages.BACK_TO_MENU.getText(), null),

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

    INCORRECT_INPUT("""
            Incorrect input! The system is foolproof! (for everyone except the programmer, at least)
            Just click the buttons, please.
            """, ButtonsMessages.BACK_TO_MENU.getText(), null),

    USER_NOT_FOUND("Error: User does did not register! Please use /start.",
            "/start", null),

    DELETED_FROM_CACHE("""
            It seems that you have waited for too long, and your challenge draft has deleted itself(30 minutes).
            Don't worry, you can just scroll up and copy your inputs from the messages you've sent before.
            """, ButtonsMessages.BACK_TO_MENU.getText(), null);


    final private String message;
    final private String[] keyboard;

    final public static String cancelMessage = ButtonsMessages.CANCEL.getText();

    Buttons(String message, String... keyboard) {
        this.message = message;
        this.keyboard = keyboard;
    }
}
