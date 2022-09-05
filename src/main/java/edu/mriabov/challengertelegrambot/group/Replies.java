package edu.mriabov.challengertelegrambot.group;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Replies {

    NEED_MORE_COINS("You lack PLACEHOLDER coins. Coins required to do this action is PLACEHOLDER."),

    CONFIRM_CHALLENGE("""
            UserCount, Area, Difficulty, message
            Are you sure you want to create this challenge? It will cost PLACEHOLDER
            """),

    ADDED_TO_CHAT("Time to change lives...")

    //!
    ;
    //!
    public final String text;
}
