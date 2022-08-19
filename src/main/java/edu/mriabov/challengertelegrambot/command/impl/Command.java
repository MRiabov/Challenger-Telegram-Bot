package edu.mriabov.challengertelegrambot.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    String alias();

    void execute(Update update);

}
