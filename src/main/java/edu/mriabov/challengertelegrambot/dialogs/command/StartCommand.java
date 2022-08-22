package edu.mriabov.challengertelegrambot.dialogs.command;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;

@RequiredArgsConstructor
@Slf4j
public class StartCommand implements AbilityExtension {

    private final TelegramBot telegramBot;

    public Ability onStart(){
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .reply(ReplyFlow.builder(telegramBot.db())
                        .action((baseAbilityBot, update) -> ButtonsUtils.buildSendMessageWithKeyboard(
                                update.getMessage().getChatId(),Buttons.ON_START_NEW_USER)

                        ).next(ReplyUtils.buildFlow(ReceivedMessages.ON_START_NEW_USER_YES,telegramBot))
                        .next(ReplyUtils.buildFlow(ReceivedMessages.ON_START_NEW_USER_NO,telegramBot))
                        .build())


        .build();
    }

}
