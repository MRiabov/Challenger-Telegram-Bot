package edu.mriabov.challengertelegrambot.dialogs.abilities;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class StartCommand implements AbilityExtension {

    private final TelegramBot telegramBot;
    private final SenderService senderService;

    public Ability onStart() {
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                //bot sends the initial message and keyboard to the user
                .action(messageContext -> telegramBot.silent().execute(
                        ButtonsUtils.buildSendMessageWithKeyboard(
                                messageContext.chatId(), Buttons.ON_START_NEW_USER)))
                //the user will reply. He will reply with either yes or no.
                .reply(ReplyFlow.builder(telegramBot.db())
                        .next(senderService.sendFlow(ReceivedMessages.ON_START_NEW_USER_YES,List.of(new ReplyFlow[]{
                                senderService.sendFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_NO,null),
                                senderService.sendFlow(ReceivedMessages.ON_START_NEW_USER_FIRST_YES,List.of(
                                        //why do we have a list in the end if whatever he sends goes to the main menu?
                                        senderService.sendFlow(ReceivedMessages.ON_START_NEW_USER_SECOND_FINISH,List.of()))


                        .next(senderService.sendFlow(ReceivedMessages.ON_START_NEW_USER_NO, List.of(new ReplyFlow[]{

                                })
                        )))

                        .build())
//todo do an imported ReplyFlow.

                .build();
    }

}
