package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TelegramUtilsTest {

    /**
     * Method under test: {@link TelegramUtils#getOffset(String)}
     */
    @Test
    void testGetOffset() {
        // Arrange, Act and Assert
        assertEquals(12, TelegramUtils.getOffset("Not all who wander are lost"));
    }

    /**
     * Method under test: {@link TelegramUtils#getOffset(String)}
     */
    @Test
    void testGetOffset3() {
        // Arrange
        String message = "word1 word2 word3 <- Here is the spot";

        // Act
        int actualOffset = TelegramUtils.getOffset(message);

        // Assert
        assertThat(actualOffset).isEqualTo(18);
    }

    @Test
    void basicInfoOfFinanceEasyIsCorrect() {
        Challenge challenge = TelegramUtils.challengeBasicInfo(new String[]{"finances", "easy"});
        assertEquals(Difficulty.EASY, challenge.getDifficulty());
        assertEquals(Area.FINANCES, challenge.getArea());
    }

    @Test
    void challengeBasicInfoOfWrongInputIsIncorrect() {
        Challenge challenge = TelegramUtils.challengeBasicInfo(new String[]{"weird input", "123"});
        assertNull(challenge.getDifficulty());
        assertNull(challenge.getArea());
    }
}