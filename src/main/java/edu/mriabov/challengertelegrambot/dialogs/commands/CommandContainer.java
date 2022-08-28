package edu.mriabov.challengertelegrambot.dialogs.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class CommandContainer {

    private final Map<String,Command> commandMap;

    public CommandContainer(@Autowired List<Command> commands) {
        commandMap = commands.stream().collect(Collectors.toUnmodifiableMap(Command::alias, Function.identity()));
    }

    public void executeByText(String text, Update update){
        commandMap.get(text).execute(update);
    }
}
