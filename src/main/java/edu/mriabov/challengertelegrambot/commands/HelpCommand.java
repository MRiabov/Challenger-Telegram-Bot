package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static edu.mriabov.challengertelegrambot.groupchat.Replies.HELP_MESSAGE;
import static edu.mriabov.challengertelegrambot.privatechat.Buttons.MENU_FAQ;
import static java.lang.Boolean.TRUE;

@Component
@RequiredArgsConstructor
public class HelpCommand implements IBotCommand {
    private final SenderService senderService;

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "get a list of commands";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (TRUE.equals(message.getChat().isUserChat())) {
            senderService.sendMessages(message.getFrom().getId(), MENU_FAQ);
        } else senderService.sendMessages(message.getChatId(), HELP_MESSAGE.text);
    }
}
