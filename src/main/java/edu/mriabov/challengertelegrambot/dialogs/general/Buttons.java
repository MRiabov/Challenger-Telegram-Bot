package edu.mriabov.challengertelegrambot.dialogs.general;

import lombok.Getter;

@Getter
public enum Buttons {
//todo buttons contain constants from other classes, because you also have to receive it.
    //buttons contain constants from other classes, because you also have to receive
    MAIN_MENU(new String[]{
            "My Challenges", null,
            "Challenge your friends",null,
            "Buy some rest...","FAQ",null});

    String[] keyboard;

    Buttons(String[] keyboard) {
        this.keyboard = keyboard;
    }
}
