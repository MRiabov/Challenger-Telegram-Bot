package edu.mriabov.challengertelegrambot.dialogs.commands;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandContainer {

    private final Map<String,Command> commandMap;

    public CommandContainer(@Autowired List<Command> commands) {
        commandMap = commands.stream().collect(Collectors.toUnmodifiableMap(Command::alias, Function.identity()));
    }

    public Command getByText(String text){
        return commandMap.get(text);
    }
}
