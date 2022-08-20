package edu.mriabov.challengertelegrambot.dialogs.messagerouters;

import edu.mriabov.challengertelegrambot.command.Command;
import edu.mriabov.challengertelegrambot.command.UnknownCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
final class CommandHandler implements MessageHandler {

    private Map<String, Command> commandMap;
    private UnknownCommand unknownCommand;

public CommandHandler(@Autowired List<Command> commands, @Autowired UnknownCommand unknownCommand){
    commandMap = commands.stream().
            collect(Collectors.toUnmodifiableMap(Command::alias, Function.identity()));

    this.unknownCommand=unknownCommand;
}

public void handleMessages(Update update){
    String text = update.getMessage().getText();
    commandMap.getOrDefault(text,unknownCommand);
    }
}
