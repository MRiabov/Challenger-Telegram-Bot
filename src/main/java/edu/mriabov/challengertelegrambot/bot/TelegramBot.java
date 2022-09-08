package edu.mriabov.challengertelegrambot.bot;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.handler.impl.GroupMasterMessageHandler;
import edu.mriabov.challengertelegrambot.handler.impl.PrivateMasterMessageHandlerImpl;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final PrivateMasterMessageHandlerImpl privateMasterMessageHandler;
    private final GroupMasterMessageHandler groupMasterMessageHandler;
    private final BotConfig config;
    private final RegistrationService registrationService;


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage()!=null) {
            if (update.getMessage().hasText() && update.getMessage().isUserMessage()) {
                log.info("Received an update with text " + update.getMessage().getText() + " in a private chat from " + update.getMessage().getChatId());
                privateMasterMessageHandler.handleMessages(update);
                log.info("Finished processing an update with text from a private chat " + update.getMessage().getChatId());
                return;
            }
            if (update.getMessage().hasText() && update.getMessage().isGroupMessage()) {
                log.info("Received an update with text " + update.getMessage().getText() + " in a groups " + update.getMessage().getChat().getTitle());
                groupMasterMessageHandler.handleMessages(update);
                log.info("Finished processing a command from a groups " + update.getMessage().getChat().getTitle());
                return;
            }
            log.error("onUpdateReceived couldn't find a method to handle a message "+update.getMessage().toString());
        }
        if (update.hasMyChatMember()){
            log.info("The bot was added to a chat. The chat is "+update.getMyChatMember().getChat().getTitle());
            registrationService.registerChat(update);
            execute(SendMessage.builder()
                    .text(Replies.ADDED_TO_CHAT.text)
                    .chatId(update.getMyChatMember().getChat().getId())
                    .build());
            return;
        }
    }
}
