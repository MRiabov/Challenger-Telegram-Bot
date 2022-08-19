package edu.mriabov.challengertelegrambot.command.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public sealed interface Command
        permits OnStartCommandImpl, UnknownCommand {

    String alias();

    void execute(Update update);

}
