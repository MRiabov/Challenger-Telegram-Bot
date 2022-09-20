package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

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
        if (message.getChat().isUserChat()) senderService.sendMessages(message.getFrom().getId(),Buttons.MENU_FAQ);
        else senderService.sendMessages(message.getFrom().getId(),Replies.HELP_MESSAGE.text);
    }
}
