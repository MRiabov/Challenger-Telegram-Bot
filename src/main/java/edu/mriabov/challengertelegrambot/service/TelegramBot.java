package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.List;

@Component
@Slf4j
public class TelegramBot extends AbilityBot {

    public static DBContext database;

    @Autowired
    public TelegramBot(@Autowired List<AbilityExtension> abilityExtensionList, BotConfig config) {
        super(config.getToken(), config.getBotName());
        addExtensions(abilityExtensionList);
        database = this.db();
    }

    @Override
    public long creatorId() {
        return 436705658;
    }
}
