package edu.mriabov.challengertelegrambot.groupchat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Replies {

    NEED_MORE_COINS("You lack PLACEHOLDER coins. Coins required to do this action is PLACEHOLDER."),

    CONFIRM_CHALLENGE("""
            UserCount, Area, Difficulty, message
            Are you sure you want to create this challenge? It will cost PLACEHOLDER
            """),

    ADDED_TO_CHAT("Time to change lives..."),

    WRONG_CHAT_TYPE("Hey! You are using this command in a wrong chat type! This command is intended elsewhere."),

    CHAT_SUCCESSFULLY_LINKED("Chat `%s` was successfully linked!"),

    INVALID_DAILY_CHALLENGE("""
            Invalid /daily arguments! Custom challenges are set in format:
            1. Time of the task, in format HH:MM
            2. [easy/medium/difficult/goal]
            3. [fitness/finances/mindfulness/relationships]
            4. Description of the message...
            So, the message should look like:
                
            /daily 14:00 medium fitness @user do 15 do a workout.
            """),

    INVALID_GLOBAL_CHALLENGE("""
            Invalid /global arguments! Custom challenges are set in format:
            1. [easy/medium/difficult/goal]
            2. [fitness/finances/mindfulness/relationships]
            3. Description of the message...
            So, the message should look like:
                
            /global medium fitness do 15 do a workout.
                    """),

    INVALID_CUSTOM_CHALLENGE("""
            Invalid /custom arguments! Custom challenges are set in format:
            1. [easy/medium/difficult/goal]
            2. [fitness/finances/mindfulness/relationships]
            3. @user1, user2, etc.
            4. Description of the message...
            So, the message should look like:
                
            /custom medium fitness @user do 15 do a workout.
            """)
    //!
    ;
    //!
    public final String text;
}
