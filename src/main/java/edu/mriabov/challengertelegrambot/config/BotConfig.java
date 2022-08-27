package edu.mriabov.challengertelegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@Setter
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    static String botName;
    @Value("${bot.token}")
    static String token;

}
