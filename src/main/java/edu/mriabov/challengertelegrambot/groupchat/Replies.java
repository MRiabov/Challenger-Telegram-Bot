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

    CHAT_SUCCESSFULLY_LINKED("Chat %s was successfully linked!"),


    //!
    ;
    //!
    public final String text;
}
