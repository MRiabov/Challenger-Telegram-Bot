package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.MessageSenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class MessageSenderServiceImpl implements MessageSenderService {

    final TelegramBot telegramBot;

    @Override
    public void sendMessage(@NotNull long chatID, @NotBlank String text) {
        SendMessage sendMessage = new SendMessage(Long.toString(chatID), text);
        sendMessageToTelegram(sendMessage);
    }

    @Override
    public void sendMessage(@NotNull long chatID,@NotBlank String text,
                            @NotNull ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage(Long.toString(chatID),text);
        sendMessage.setReplyMarkup(markup);
        sendMessageToTelegram(sendMessage);
    }

    @Override
    public void sendMessage(@NotNull long chatID, @NotNull Buttons button) {
        sendMessage(chatID, button.getMessage(), TelegramUtils.ArrayToReplyMarkup(button.getKeyboard()));
    }

    private void sendMessageToTelegram(SendMessage sendMessage){
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
