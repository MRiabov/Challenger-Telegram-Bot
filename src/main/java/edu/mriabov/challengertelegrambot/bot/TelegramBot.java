package edu.mriabov.challengertelegrambot.bot;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.handler.impl.GroupMasterMessageHandler;
import edu.mriabov.challengertelegrambot.handler.impl.PrivateMasterMessageHandlerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final PrivateMasterMessageHandlerImpl privateMasterMessageHandler;
    private final GroupMasterMessageHandler groupMasterMessageHandler;
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
            log.info("Received an update with text in a private chat from" + update.getMessage().getChatId());
            privateMasterMessageHandler.handleMessages(update);
            log.info("Finished processing an update with text from a private chat" + update.getMessage().getChatId());
        }
        if (update.getMessage().hasText()&&update.getMessage().isGroupMessage()){
            log.info("Received an update with text in a group" + update.getMessage().getChat().getTitle());
            groupMasterMessageHandler.handleMessages(update);
        }
    }
}
