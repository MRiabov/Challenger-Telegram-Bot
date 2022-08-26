package edu.mriabov.challengertelegrambot.reply.replyflows;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.PublicButtonsMessages;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.sendMenu;

@Component
@RequiredArgsConstructor
public class ChallengeYourFriends implements AbilityExtension {

    private final ReplyBuilderService replyBuilderService;

    public ReplyFlow challengeYourFriends() {
        Challenge challenge = new Challenge();
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().getText().equals(ReceivedMessages.MENU_CHALLENGE_YOUR_FRIENDS.getReceivedMessage()))
                .action((baseAbilityBot, update) -> sendMenu(update, baseAbilityBot, Buttons.MENU_CHALLENGE_YOUR_FRIENDS))
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectDifficulty(challenge))
                .build();
    }

    private ReplyFlow selectDifficulty(Challenge challenge) {
        return ReplyFlow.builder(TelegramBot.database, 311)
                .onlyIf(TelegramUtils::isNotCancel)
                .action((baseAbilityBot, update) -> {
                    challenge.setDifficulty(switchDifficulty(update));
                    if (challenge.getDifficulty() != Difficulty.INCORRECT) {
                        sendMenu(update, baseAbilityBot, Buttons.AREA_SELECTION);
                    } else {
                        baseAbilityBot.silent().send("Next time just click the buttons, please", update.getMessage().getChatId());
                        sendMenu(update, baseAbilityBot, Buttons.MAIN_MENU);
                    }
                })
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectArea(challenge))
                .build();
    }

    private ReplyFlow selectArea(Challenge challenge) {
        return ReplyFlow.builder(TelegramBot.database, 312)
                .onlyIf(update -> challenge.getDifficulty() != Difficulty.INCORRECT)
                .action((baseAbilityBot, update) -> {
                    if (challenge.getDifficulty() != Difficulty.INCORRECT) {
                        if (true/*user has only one chat*/) sendMenu(update, baseAbilityBot, Buttons.ONLY_ONE_CHAT);
                        else sendMenu(update, baseAbilityBot, Buttons.AREA_SELECTION);
                    } else {
                        baseAbilityBot.silent().send("Next time input the correct messages, please", update.getMessage().getChatId());
                        sendMenu(update, baseAbilityBot, Buttons.MAIN_MENU);
                    }
                })
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectChat(challenge))
                .build();
    }

    private ReplyFlow selectChat(Challenge challenge) {
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().hasText())
                .action((baseAbilityBot, update) -> {
                    sendMenu(update, baseAbilityBot, Buttons.SELECT_USER);
                })//todo if user has only 1 chat, set the chat automatically.
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectUser(challenge))
                .build();
    }

    private Reply selectUser(Challenge challenge) {
        return Reply.of((baseAbilityBot, update) -> {
            challenge.setArea(switchArea(update));
            if (challenge.getArea() != Area.INCORRECT) {
                //todo if user has only 1 chat, set the chat automatically
                sendMenu(update, baseAbilityBot, Buttons.SELECT_USER);
                //todo get from db
            }
        }, update -> challenge.getArea() != Area.INCORRECT);
    }


    private Difficulty switchDifficulty(Update update) {//todo to util
        //no, it was impossible to do with switch. switch doesn't accept enums...
        String message = update.getMessage().getText();
        if (message.equals(PublicButtonsMessages.EASY_DIFFICULTY.getText()))
            return Difficulty.EASY;
        if (message.equals(PublicButtonsMessages.MEDIUM_DIFFICULTY.getText()))
            return Difficulty.MEDIUM;
        if (message.equals(PublicButtonsMessages.DIFFICULT_DIFFICULTY.getText()))
            return Difficulty.DIFFICULT;
        if (message.equals(PublicButtonsMessages.GOAL_DIFFICULTY.getText()))
            return Difficulty.GOAL;
        //todo this shouldn't be, but I don't see the other way yet
        return Difficulty.INCORRECT;
    }

    private Area switchArea(Update update) {
        String message = update.getMessage().getText();
        if (message.equals(PublicButtonsMessages.RELATIONSHIPS_AREA.getText()))
            return Area.RELATIONSHIPS;
        if (message.equals(PublicButtonsMessages.MINDFULNESS_AREA.getText()))
            return Area.MINDFULNESS;
        if (message.equals(PublicButtonsMessages.FINANCES_AREA.getText()))
            return Area.FINANCES;
        if (message.equals(PublicButtonsMessages.FITNESS_AREA.getText()))
            return Area.FITNESS;
        return Area.INCORRECT;
    }

}
