package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.cache.impl.ChallengeCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.ChallengeService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmChallengeCommand implements IBotCommand {

    private final ChallengeCache challengeCache;
    private final ChallengeService challengeService;
    private final SenderService senderService;
    private final BillingService billingService;
    private final ValidatorService validatorService;

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (validatorService.isNotGroupChat(message)) return;
        if (!challengeCache.contains(message.getFrom().getId())) {
            senderService.replyToMessage(message, Replies.NOTHING_TO_CONFIRM.text);
            return;
        }
        Challenge challenge = challengeCache.get(message.getFrom().getId());
        if (validatorService.isChallengeInvalid(challenge)) {
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
            return;
        }
        if (!billingService.isEnoughCoinsForChallenge(challenge)) {
            senderService.replyToMessage(message, Replies.NEED_MORE_COINS.text);
            return;
        }
        billingService.billCoins(message.getFrom().getId(), billingService.challengePrice(challenge));
        challengeService.save(challenge);
        senderService.replyToMessage(message, Replies.CHALLENGE_CREATION_SUCCESSFUL.text);
        for (edu.mriabov.challengertelegrambot.dao.model.User challengeUser : challenge.getUsers()) {
            senderService.sendMessages(challengeUser.getTelegramId(), Buttons.ASSIGNED_NEW_CHALLENGE);
        }
        log.info("Successfully confirmed a challenge from user " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + challenge);
    }

    @Override
    public String getCommandIdentifier() {
        return "confirm";
    }

    @Override
    public String getDescription() {
        return "Confirm your created challenge.";
    }


}
