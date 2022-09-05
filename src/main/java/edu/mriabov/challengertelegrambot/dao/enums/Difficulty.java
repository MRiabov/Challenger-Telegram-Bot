package edu.mriabov.challengertelegrambot.dao.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Difficulty {

    EASY(2,1),
    MEDIUM(5,2),
    DIFFICULT(10,4),
    GOAL(30,10);//todo probably variable;

    public final int price;
    public final int reward;
}
