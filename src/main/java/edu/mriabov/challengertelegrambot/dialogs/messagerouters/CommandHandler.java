package edu.mriabov.challengertelegrambot.dialogs.messagerouters;

import edu.mriabov.challengertelegrambot.command.Command;
import edu.mriabov.challengertelegrambot.command.UnknownCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
final class CommandHandler implements MessageHandler {
//todo what to do with Unknown command?
    private HashMap<String, Command> commandMap;
    private UnknownCommand unknownCommand;

public CommandHandler(@Autowired List<Command> commands, @Autowired UnknownCommand unknownCommand){
    commandMap = (HashMap<String, Command>) commands.stream().
            collect(Collectors.toUnmodifiableMap(Command::alias, Function.identity()));

    this.unknownCommand=unknownCommand;
}

public void routeMessages(Update update){
    String text = update.getMessage().getText();
    if (commandMap.containsKey(text)) commandMap.get(text).execute(update);
    else unknownCommand.execute(update);
    }
}
