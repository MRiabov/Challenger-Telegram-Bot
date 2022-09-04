package edu.mriabov.challengertelegrambot.bot;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final MessageHandler messageHandler;
    private final BotConfig config;


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()&&update.getMessage().isUserMessage()) {
            log.info("Received an update with text.");
            messageHandler.handleMessages(update);
            log.info("Finished processing an update with text.");
        }
        if (update.getMessage().isCommand()&&update.getMessage().isGroupMessage()){

        }
    }
}
