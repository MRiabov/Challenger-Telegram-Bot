package edu.mriabov.challengertelegrambot.dao.enums;

public enum Difficulty {

    EASY("EASY"),
    MEDIUM("MEDIUM"),
    DIFFICULT("DIFFICULT"),
    GOAL("GOAL");

    private final String text;

    Difficulty(String text) {
        this.text=text;
    }
}
