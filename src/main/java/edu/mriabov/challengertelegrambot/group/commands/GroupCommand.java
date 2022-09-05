package edu.mriabov.challengertelegrambot.group.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface GroupCommand{

    String alias();
    void execute(Message message);

}
