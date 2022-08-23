package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dialogs.abilities.StartCommand;
import edu.mriabov.challengertelegrambot.reply.MasterReplyFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;

@Component
public class TelegramBot extends AbilityBot {

    public static DBContext database;

    @Autowired
    public TelegramBot(BotConfig config, @Autowired List<AbilityExtension> abilityExtensionList) {
        super(config.getToken(), config.getBotName());
        addExtensions(abilityExtensionList);
        database=this.db();
    }

    @Override
    public long creatorId() {
        return 436705658;
    }
}
