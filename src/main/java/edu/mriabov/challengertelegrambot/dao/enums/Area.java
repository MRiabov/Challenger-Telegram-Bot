package edu.mriabov.challengertelegrambot.dao.enums;

import edu.mriabov.challengertelegrambot.privatechat.LogicButtonsMessages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Area {

    FINANCES(LogicButtonsMessages.FINANCES_AREA.getText()),
    FITNESS(LogicButtonsMessages.FITNESS_AREA.getText()),
    MINDFULNESS(LogicButtonsMessages.MINDFULNESS_AREA.getText()),
    RELATIONSHIPS(LogicButtonsMessages.RELATIONSHIPS_AREA.getText());

    public final String text;

}
