package edu.mriabov.challengertelegrambot.privatechat.dialogs.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandContainer {

    private final Map<String, PrivateCommand> commandMap;

    public CommandContainer(@Autowired List<PrivateCommand> commands) {
        commandMap = commands.stream().collect(Collectors.toUnmodifiableMap(PrivateCommand::alias, Function.identity()));
    }

    public void executeByText(Message message) {
        commandMap.get(message.getText()).execute(message);
    }
}
