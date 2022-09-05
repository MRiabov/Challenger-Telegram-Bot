package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCustomChallenge extends BotCommand implements Command {

    private final InlineChallengeCreatorService inlineChallengeCreatorService;
    private final SenderService senderService;

    @Override
    public String alias() {
        return "/custom";
    }

    @Override
    public String scope() {
        return new BotCommandScopeAllGroupChats().getType();
    }

    @Override
    public void execute(Message message) {
        Optional<Challenge> challenge = inlineChallengeCreatorService.createChallenge(message);
        if (challenge.isEmpty()) senderService.replyToMessage(message,"FAILURE");
        senderService.replyToMessage(message,"SUCCESS");
    }
}
