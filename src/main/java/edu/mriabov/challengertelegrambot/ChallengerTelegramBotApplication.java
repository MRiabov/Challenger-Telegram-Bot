package edu.mriabov.challengertelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan

@SpringBootApplication
public class ChallengerTelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChallengerTelegramBotApplication.class, args);
    }

}
