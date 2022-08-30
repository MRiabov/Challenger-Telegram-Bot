package edu.mriabov.challengertelegrambot.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DynamicButtonServiceImplTest {
    DynamicButtonServiceImpl dynamicButtonService = new DynamicButtonServiceImpl();
    @Test
    void createMarkup() {
        var test =dynamicButtonService.createMarkup("user",9);
        test = dynamicButtonService.createMarkup("user", 2);
    }
}