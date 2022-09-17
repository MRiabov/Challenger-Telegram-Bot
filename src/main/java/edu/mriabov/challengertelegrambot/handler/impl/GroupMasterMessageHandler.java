package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GroupMasterMessageHandler implements MessageHandler {

    @Override
    public void handleMessages(Update update) {

    }
}
