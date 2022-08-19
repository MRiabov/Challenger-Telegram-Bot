package edu.mriabov.challengertelegrambot.dialogs.general;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ButtonsMessages {

    MENU_CHALLENGES("My Challenges"),
    MENU_CHALLENGE_YOUR_FRIENDS("Challenge your friends"),
    MENU_REST("Buy some rest..."),
    MENU_FAQ("FAQ");

;
    private final String txt;
}
