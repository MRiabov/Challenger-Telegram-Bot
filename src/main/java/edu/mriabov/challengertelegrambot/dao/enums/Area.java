package edu.mriabov.challengertelegrambot.dao.enums;

import edu.mriabov.challengertelegrambot.privatechat.LogicButtonsMessages;
import lombok.RequiredArgsConstructor;

import static edu.mriabov.challengertelegrambot.privatechat.LogicButtonsMessages.FINANCES_AREA;

@RequiredArgsConstructor
public enum Area {

    FINANCES(FINANCES_AREA.getText()),
    FITNESS(LogicButtonsMessages.FITNESS_AREA.getText()),
    MINDFULNESS(LogicButtonsMessages.MINDFULNESS_AREA.getText()),
    RELATIONSHIPS(LogicButtonsMessages.RELATIONSHIPS_AREA.getText());

    public final String text;

}
