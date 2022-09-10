package edu.mriabov.challengertelegrambot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllChatAdministrators;

public class CreateGlobalChallenge implements Command{
    @Override
    public String alias() {
        return "/global";
    }

    @Override
    public String scope() {
        return new BotCommandScopeAllChatAdministrators().getType();
    }

    @Override
    public void execute(Message message) {

    }
}
