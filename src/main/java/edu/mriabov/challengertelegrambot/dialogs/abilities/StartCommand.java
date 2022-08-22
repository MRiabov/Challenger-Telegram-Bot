package edu.mriabov.challengertelegrambot.dialogs.abilities;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
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

@RequiredArgsConstructor
@Slf4j
@Service
public class StartCommand implements AbilityExtension {

    private final TelegramBot telegramBot;

    public Ability onStart() {
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(messageContext -> telegramBot.silent().execute(
                        ButtonsUtils.buildSendMessageWithKeyboard(
                                messageContext.chatId(), Buttons.ON_START_NEW_USER)))

                .reply(ReplyFlow.builder(telegramBot.db())
                        .onlyIf(update -> update.getMessage().getText().equals(ReceivedMessages.ON_START_NEW_USER_FIRST_YES.getText()))
                        .next(ReplyFlow.builder(telegramBot.db())
                                .action(telegramBot.silent().execute())


                                .build())
                        .next(ReplyUtils.buildFlow(ReceivedMessages.ON_START_NEW_USER_NO, telegramBot))

                        .build())
//todo do an imported ReplyFlow.

                .build();
    }

}
