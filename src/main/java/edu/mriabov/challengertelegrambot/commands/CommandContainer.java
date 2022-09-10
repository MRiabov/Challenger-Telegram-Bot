package edu.mriabov.challengertelegrambot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandContainer {

    private final Map<String, Command> commandMap;
    private final UnknownCommand unknownCommand;

    public CommandContainer(@Autowired List<Command> commands, @Autowired UnknownCommand unknownCommand) {
        commandMap = commands.stream().collect(Collectors.toUnmodifiableMap(Command::alias, Function.identity()));
        this.unknownCommand = unknownCommand;
    }

    public void executeByText(Message message) {
        int commandEnd = message.getText().length();
        for (int i = 0; i < message.getText().toCharArray().length; i++) {
            if (message.getText().charAt(i) == ' ' || message.getText().charAt(i) == '@') {
                commandEnd = i;
                break;
            }
        }
        commandMap.getOrDefault(message.getText().substring(0,commandEnd),unknownCommand).execute(message);
    }
}
