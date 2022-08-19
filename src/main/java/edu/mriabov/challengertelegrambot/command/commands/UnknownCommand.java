package edu.mriabov.challengertelegrambot.command.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public final class UnknownCommand implements Command {
    @Override
    public String alias() {
        return "unknownCommand";
    }

    @Override
    public void execute(Update update) {

    }
}
