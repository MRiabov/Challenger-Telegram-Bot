package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    String alias();
    void execute(Message message);
}
