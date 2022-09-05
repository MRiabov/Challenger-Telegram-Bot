package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;

@Service
@RequiredArgsConstructor
public class CreateCustomChallenge implements Command {

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
        inlineChallengeCreatorService.createChallenge(message);
        senderService.replyToMessage(message,"PLACEHOLDER. THIS IS A CHALLENGE CREATION MESSAGE");
    }
}
