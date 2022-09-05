package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface PrivateCommand {

    String alias();
    void execute(Message message);
}
