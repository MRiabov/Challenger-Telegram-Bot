package edu.mriabov.challengertelegrambot.dialogs.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{


    @Override
    public String alias() {
        return "/start";
    }

    @Override
    public void execute(Update update) {

    }
}
