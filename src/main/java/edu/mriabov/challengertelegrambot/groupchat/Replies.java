package edu.mriabov.challengertelegrambot.groupchat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Replies {

    NEED_MORE_COINS("You lack PLACEHOLDER coins. Coins required to do this action is PLACEHOLDER."),

    CONFIRM_CHALLENGE("""
            UserCount, Area, Difficulty, message
            Are you sure you want to create this challenge? It will cost PLACEHOLDER
            """),

    USER_NOT_REGISTERED("User not registered! Please register through this %d"),

    ADDED_TO_CHAT("Time to change lives..."),

    WRONG_CHAT_TYPE("Hey! You are using this command in a wrong chat type! This command is intended elsewhere."),

    CHAT_SUCCESSFULLY_LINKED("Chat `%s` was successfully linked!"),

    INVALID_DAILY_CHALLENGE("""
            Invalid /daily arguments! Custom challenges are set in format:
            1. [fitness/finances/mindfulness/relationships]
            2. [easy/medium/difficult/goal]
            3. Description of the message.
            If you would like to set a time for the task, e.g. 14:00, just tag it somewhere along the lines.
            So, the message should look like:
            
            /daily medium fitness do a workout at 14:00.
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
