package edu.mriabov.challengertelegrambot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandContainer {

    private final Map<String, BotCommand> commandMap;

    public CommandContainer(@Autowired List<BotCommand> commands) {
        commandMap = commands.stream().collect
                (Collectors.toUnmodifiableMap(BotCommand::getCommandIdentifier, Function.identity()));
    }
}
