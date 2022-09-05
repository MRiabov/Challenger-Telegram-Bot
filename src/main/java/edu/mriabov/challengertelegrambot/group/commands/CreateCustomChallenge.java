package edu.mriabov.challengertelegrambot.group.commands;

import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.InlineChallengeCreatorService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class CreateCustomChallenge implements GroupCommand {

    private final InlineChallengeCreatorService inlineChallengeCreatorService;
    private final BillingService billingService;
    private final SenderService senderService;

    @Override
    public String alias() {
        return "/custom";
    }

    @Override
    public void execute(Message message) {
        inlineChallengeCreatorService.createChallenge(message);
        senderService.replyToMessage(message,"message");
    }
}
