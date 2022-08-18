package edu.mriabov.challengertelegrambot.repository.enums;

import lombok.Getter;

@Getter
public enum Area {

    RELATIONSHIPS("RELATIONSHIPS"),
    FITNESS("FITNESS"),
    MINDFULNESS("MINDFULNESS"),
    FINANCES("FINANCES");

    private final String text;

    Area(String text) {
        this.text = text;
    }
}
