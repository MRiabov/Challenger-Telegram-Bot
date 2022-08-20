package edu.mriabov.challengertelegrambot.dialogs.general;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Buttons {
    //todo message before every challenge. Dashboard, current challenges, challenge ur friends... everything
    // todo that also means that i lack arguments in wherever this is accepted...
    //todo there should be 0 "string" in this class.
    MAIN_MENU("PLACEHOLDER",
            new String[]{
                    ButtonsMessages.MENU_CHALLENGES.getTxt(), null,
                    ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getTxt(), null,
                    ButtonsMessages.MENU_REST.getTxt(), ButtonsMessages.MENU_FAQ.getTxt(), null}),
    MY_CHALLENGES("PLACEHOLDER", null),
    CHALLENGE_YOUR_FRIENDS("PLACEHOLDER", null),
    FAQ("PLACEHOLDER", null),
    UNKNOWN_COMMAND(ButtonsMessages.UNKNOWN_COMMAND.getTxt(), MAIN_MENU.getKeyboard());


    final private String message;
    final private String[] keyboard;
}
