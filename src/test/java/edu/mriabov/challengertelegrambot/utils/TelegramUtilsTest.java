package edu.mriabov.challengertelegrambot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUtilsTest {

    @Test
    void ProducesCorrectLists() {
        var keyboard = TelegramUtils.ArrayToReplyMarkup(new String[]{"1","2",null,"3",null,"4",null});
        assertEquals("1",keyboard.getKeyboard().get(0).get(0).getText());
        assertEquals("2",keyboard.getKeyboard().get(0).get(1).getText());
        assertEquals("3",keyboard.getKeyboard().get(1).get(0).getText());
        assertEquals("4",keyboard.getKeyboard().get(2).get(0).getText());
    }

    void moreRowsThanNeededThrowsOutOfBounds(){
        var keyboard = TelegramUtils.ArrayToReplyMarkup(new String[]{"1","2",null,"3",null,"4",null});
        assertThrows(ArrayIndexOutOfBoundsException.class,keyboard.getKeyboard().get(3).get(0).getText());
    }
}