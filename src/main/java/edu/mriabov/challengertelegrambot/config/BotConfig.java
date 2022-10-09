package edu.mriabov.challengertelegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@Setter
@PropertySource("application.properties")
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    String botName;
    String token;

}
