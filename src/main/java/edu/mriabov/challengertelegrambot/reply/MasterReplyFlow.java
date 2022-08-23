package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MasterReplyFlow {

    private final ReplyBuilderService replyBuilderService;

    public ReplyFlow welcomeFlow() {
        return ReplyFlow.builder(TelegramBot.database)
                .next(replyBuilderService.buildFlow(ReceivedMessages.ON_START_NEW_USER_NO, mainMenuFlow()))
                .next(replyBuilderService.buildFlow(ReceivedMessages.ON_START_NEW_USER_YES, List.of(
                                replyBuilderService.buildFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_NO, mainMenuFlow()),
                                replyBuilderService.buildFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_YES, List.of(
                                        replyBuilderService.buildFlow(ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH, mainMenuFlow())
                                ))
                        )
                ))
                .build();
    }
//todo FUUUCK IT WAS ALWAYS HEEEREEE
    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public ReplyFlow mainMenuFlow() {
        return ReplyFlow.builder(TelegramBot.database)
                .action((baseAbilityBot, update) ->
                        baseAbilityBot.silent().execute(s))
//                        senderService.sendMessages(update.getMessage().getChatId(), Buttons.MAIN_MENU))
                .next(challengeCreateFlow())
                .next(myChallengesFlow())
                .build();
    }

    private ReplyFlow myChallengesFlow(){
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().getText().equals(Buttons.MY_CHALLENGES.getMessage()))
                .build();
    }

    private ReplyFlow challengeCreateFlow(){
        return ReplyFlow.builder(TelegramBot.database)
                .onlyIf(update -> update.getMessage().getText().equals(Buttons.CHALLENGE_YOUR_FRIENDS.getMessage()))
                .build();
    }
}
