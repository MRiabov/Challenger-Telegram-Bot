package edu.mriabov.challengertelegrambot.command;

import edu.mriabov.challengertelegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Component
@RequiredArgsConstructor
public class OnStartCommandImpl extends BotCommand {

    private final UserRepository userRepository;



}
