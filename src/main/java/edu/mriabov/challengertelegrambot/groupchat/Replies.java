package edu.mriabov.challengertelegrambot.groupchat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Replies {

    HELP_MESSAGE("""
    Here's a list of commands:
    /help - get this message,
    /custom - create a custom challenge for someone. Costs coins.
    
    Available only for admins:
    /global - create a non-recurring challenge for everyone.
    /daily - the primary source of economy in the group. These challenges provide everyone with challenges and with coins, as a result.
    Use it wisely.
    """),

    NEED_MORE_COINS("You lack don't have enough coins! You current coin count is %6$d."),

    CONFIRM_CHALLENGE("""
            So, this is your challenge:
            %10$s
            """),

    USER_NOT_REGISTERED("User not registered! Please register through this %s"),

    ADDED_TO_CHAT("Time to change lives..."),

    WRONG_CHAT_TYPE("Hey! You are using this command in a wrong chat type! This command is intended elsewhere."),

    USER_NOT_ADMIN("This command is accessible to admins only admins!"),

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
                
            /custom medium fitness @user do a workout.
            """),
    NOTHING_TO_CONFIRM("Nothing to confirm! How about creating a challenge for someone? Use /custom."),
    CHALLENGE_CREATION_SUCCESSFUL("Success! Challenge was successfully added."),
    //!
    ;
    //!
    public final String text;
}
