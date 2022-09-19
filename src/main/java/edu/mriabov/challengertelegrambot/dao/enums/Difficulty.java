package edu.mriabov.challengertelegrambot.dao.enums;

import edu.mriabov.challengertelegrambot.privatechat.LogicButtonsMessages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Difficulty {

    EASY(2,1, LogicButtonsMessages.EASY_DIFFICULTY.getText()),
    MEDIUM(5,2, LogicButtonsMessages.MEDIUM_DIFFICULTY.getText()),
    DIFFICULT(10,4, LogicButtonsMessages.DIFFICULT_DIFFICULTY.getText()),
    GOAL(30,10, LogicButtonsMessages.GOAL_DIFFICULTY.getText());//todo probably variable;

    public final int price;
    public final int reward;
    public final String text;
}
