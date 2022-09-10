package edu.mriabov.challengertelegrambot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public class CreateDailyChallenge implements Command{
    @Override
    public String alias() {
        return "/daily";
    }

    @Override
    public String scope() {
        return null;
    }

    @Override
    public void execute(Message message) {

    }
}
