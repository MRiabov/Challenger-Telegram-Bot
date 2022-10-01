package edu.mriabov.challengertelegrambot.utils;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ButtonsMappingUtilsTest {
    /**
     * Method under test: {@link ButtonsMappingUtils#createDynamicMarkup(String, int)}
     */
    @Test
    void testCreateDynamicMarkup() {
        // Arrange, Act and Assert
        List<KeyboardRow> keyboard = ButtonsMappingUtils.createDynamicMarkup("Appendix", 3).getKeyboard();
        assertEquals(2, keyboard.size());
        KeyboardRow highestRow = keyboard.get(0);
        assertEquals("2️⃣ Appendix", highestRow.get(1).getText());
        KeyboardRow lowestRow = keyboard.get(1);
        assertEquals("❌Cancel", lowestRow.get(1).getText());
        assertEquals("⏪📄 Appendix", lowestRow.get(0).getText());
        assertEquals("1️⃣ Appendix", highestRow.get(0).getText());
        assertEquals("⏩📄 Appendix", lowestRow.get(2).getText());
        assertEquals("3️⃣ Appendix", highestRow.get(2).getText());
        assertThat(highestRow.size()).isEqualTo(3);
        assertThat(lowestRow.size()).isEqualTo(3);
    }

    /**
     * Method under test: {@link ButtonsMappingUtils#createDynamicMarkup(String, int)}
     */
    @Test
    void testCreateDynamicMarkup2() {
        // Arrange, Act and Assert
        List<KeyboardRow> keyboard = ButtonsMappingUtils.createDynamicMarkup("foo", 1).getKeyboard();
        assertEquals(2, keyboard.size());
        KeyboardRow getResult = keyboard.get(1);
        assertEquals("❌Cancel", getResult.get(1).getText());
        assertEquals("⏪📄 foo", getResult.get(0).getText());
        assertEquals("⏩📄 foo", getResult.get(2).getText());
        assertEquals("1️⃣ foo", keyboard.get(0).get(0).getText());
    }
}

