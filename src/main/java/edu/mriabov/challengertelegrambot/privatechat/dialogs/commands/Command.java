package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    String alias();
    void execute(Update update);
}
