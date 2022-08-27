package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.List;


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

}
