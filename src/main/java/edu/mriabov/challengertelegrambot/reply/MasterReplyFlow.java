package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.PublicButtonsMessages;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static edu.mriabov.challengertelegrambot.utils.ReplyUtils.sendMenu;


@Component
public class MasterReplyFlow implements AbilityExtension {

    //ids are to be labeled: [x]00 for separate menu buttons, 0[x]0 for features in menu, 00[x] for subfeatures.
    //todo constants instead of methods and a constructor

    private final ReplyBuilderService replyBuilderService;
    //    private final ReplyFlow welcomeFlow = welcome();
    //    menu is for a public replies, flow is for partially inaccessible flows.

    @Autowired
    public MasterReplyFlow(ReplyBuilderService replyBuilderService) {
        this.replyBuilderService = replyBuilderService;
    }

    public Reply mainMenu() {
        return replyBuilderService.buildSimpleReply(ReceivedMessages.MAIN_MENU);
    }

    public Reply myChallenges() {
        return replyBuilderService.buildSimpleReply(ReceivedMessages.MENU_MY_CHALLENGES);
    }

    public ReplyFlow welcome() {
        return ReplyFlow.builder(TelegramBot.database, 1)
                .next(replyBuilderService.buildSimpleFlow(2, ReceivedMessages.ON_START_NEW_USER_NO, mainMenu()))
                .next(replyBuilderService.buildSimpleFlow(3, ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                        replyBuilderService.buildSimpleFlow(4, ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenu()),
                                        replyBuilderService.buildSimpleFlow(5, ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                                replyBuilderService.buildSimpleFlow(6, ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenu())
                                        ))
                                )
                        )
                )
                .build();
    }

    //my challenges menu
    public ReplyFlow setGoal() {
        return replyBuilderService.buildSimpleFlow(220, ReceivedMessages.SET_GOAL,
                List.of(
                        replyBuilderService.cancelMessage(Buttons.MENU_MY_CHALLENGES),
                        ReplyFlow.builder(TelegramBot.database)
                                .onlyIf(TelegramUtils::isNotCancel)
                                .action(((baseAbilityBot, update) -> {
                                    //todo db operation+if have enough balance
                                    sendMenu(update, baseAbilityBot, Buttons.MENU_MY_CHALLENGES);
                                }))
                                .next(myChallenges())//todo time, area and whatever selection.
                                .build())
        );
    }

    public ReplyFlow skipChallenge() {
        return replyBuilderService.buildSimpleFlow(230, ReceivedMessages.SKIP_CHALLENGES, List.of(
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isCancel)
                        .action((baseAbilityBot, update) -> sendMenu(update, baseAbilityBot, Buttons.SKIP_CHALLENGES))
                        .build(),
                ReplyFlow.builder(TelegramBot.database)
                        .onlyIf(TelegramUtils::isNotCancel)
                        .action((baseAbilityBot, update) -> {
                            //todo dbOperation
                            sendMenu(update, baseAbilityBot, Buttons.SKIP_CHALLENGES);
                        })
                        .build()
        ));
    }

    public ReplyFlow markAsCompleted() {
        return replyBuilderService.buildSimpleFlow(240, ReceivedMessages.MARK_CHALLENGE_AS_COMPLETED,
                List.of(
                        replyBuilderService.cancelMessage(Buttons.MENU_MY_CHALLENGES),
                        Reply.of(
                                ((baseAbilityBot, update) -> {
                                    //todo save!!!
                                    baseAbilityBot.silent().send("Success!", update.getMessage().getChatId());
                                }),
                                TelegramUtils::isNotCancel)
                )
        );
    }

    //ChallengeCreateFlow
    public ReplyFlow selectDifficulty() {
        Challenge challenge = new Challenge();
        return replyBuilderService.buildSimpleFlow(310, ReceivedMessages.MENU_CHALLENGE_YOUR_FRIENDS, List.of(
                        replyBuilderService.cancelMessage(Buttons.MAIN_MENU),
                        ReplyFlow.builder(TelegramBot.database, 311)
                                .onlyIf(TelegramUtils::isNotCancel)
                                .action((baseAbilityBot, update) -> {
                                    challenge.setDifficulty(switchDifficulty(update));
                                    if (challenge.getDifficulty() != Difficulty.INCORRECT) {
                                        sendMenu(update, baseAbilityBot, Buttons.AREA_SELECTION);
                                    } else {
                                        baseAbilityBot.silent().send("Next time input the correct messages, please",update.getMessage().getChatId());
                                        sendMenu(update, baseAbilityBot, Buttons.MAIN_MENU);
                                    }
                                })
                                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                                .next(selectArea(challenge))

                                .build()
                )
        );
    }

    private ReplyFlow selectArea(Challenge challenge) {
        return ReplyFlow.builder(TelegramBot.database, 312)
                .onlyIf(update -> challenge.getDifficulty() != Difficulty.INCORRECT)
                .action((baseAbilityBot, update) -> {
                    if (challenge.getDifficulty() != Difficulty.INCORRECT) {
                        if (true/*user has only one chat*/)
                        sendMenu(update, baseAbilityBot, Buttons.AREA_SELECTION);
                    } else {
                        baseAbilityBot.silent().send("Next time input the correct messages, please",update.getMessage().getChatId());
                        sendMenu(update, baseAbilityBot, Buttons.MAIN_MENU);
                    }})
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectChat(challenge))
                .build();
    }

    private ReplyFlow selectChat(Challenge challenge){
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().hasText())
                .action((baseAbilityBot, update) -> challenge.setChat())//todo if user has only 1 chat, set the chat automatically.
                .next(selectUser())
                .next()
                .build();
    }

    private ReplyFlow selectUser(Challenge challenge) {
        return ReplyFlow.builder(TelegramBot.database, 313)
                .onlyIf(update -> challenge.getArea() != Area.INCORRECT)
                .action((baseAbilityBot, update) -> {
                    challenge.setArea(switchArea(update));
                    if (challenge.getArea() != Area.INCORRECT) {
                        //todo if user has only 1 chat, set the chat automatically
                        sendMenu(update, baseAbilityBot, Buttons.SELECT_USER);
                        //todo get from db
                    }
                })
                .next(replyBuilderService.cancelMessage(Buttons.MAIN_MENU))
                .next(selectChat(challenge))
                .build();
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
