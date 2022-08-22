package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.abilities.StartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;

@Component
public class TelegramBot extends AbilityBot {

    final BotConfig config;

    @Autowired
    public TelegramBot(BotConfig config/*, @Autowired List<AbilityExtension> list*/) {
        super(config.getToken(), config.getBotName());
        this.config = config;
        addExtensions(new StartCommand(this));
    }

    @Override
    public long creatorId() {
        return 436705658;
    }
}
