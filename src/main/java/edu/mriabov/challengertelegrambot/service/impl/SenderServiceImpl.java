package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final AbilityBot abilityBot;

    @Override
    public void sendMessages(long chatID, String message) {
        abilityBot.silent().send(message, chatID);
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, String message, ReplyKeyboardMarkup markup) {
        abilityBot.sender().execute(SendMessage.builder()
                .chatId(chatID)
                .text(message)
                .replyMarkup(markup).build());
    }

    @SneakyThrows
    @Override
    public void sendMessages(long chatID, Buttons buttons) {
        abilityBot.sender().execute(ButtonsUtils.buildSendMessageWithKeyboard(chatID, buttons));
    }

    @SneakyThrows
    @Override
    public void sendMessages(SendMessage sendMessage) {
        abilityBot.sender().execute(sendMessage);
    }

}
