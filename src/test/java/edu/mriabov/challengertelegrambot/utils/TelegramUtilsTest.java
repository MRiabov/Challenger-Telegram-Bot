package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUtilsTest {

    @Test
    void basicInfoOfFinanceEasyIsCorrect() {
        Challenge challenge = TelegramUtils.challengeBasicInfo(new String[]{"finance", "easy"});
        assertEquals(Difficulty.EASY,challenge.getDifficulty());
        assertEquals(Area.FINANCES,challenge.getArea());
    }
}