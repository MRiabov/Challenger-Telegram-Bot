package edu.mriabov.challengertelegrambot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    String alias();

    String scope();

    void execute(Message message);
}
