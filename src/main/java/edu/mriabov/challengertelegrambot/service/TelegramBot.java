package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;

@Component
public class TelegramBot extends AbilityBot {

    final BotConfig config;

    @Autowired
    public TelegramBot(BotConfig config) {
        super(config.getToken(), config.getBotName());
        this.config = config;

    }

    @Override
    public long creatorId() {
        return 436705658;
    }
}
