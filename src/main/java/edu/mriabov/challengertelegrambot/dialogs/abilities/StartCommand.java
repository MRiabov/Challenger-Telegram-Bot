package edu.mriabov.challengertelegrambot.dialogs.abilities;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.reply.MasterReplyFlow;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;

@RequiredArgsConstructor
@Slf4j
@Service
public class StartCommand implements AbilityExtension {
    private final MasterReplyFlow masterReplyFlow;

    public Ability onStart() {
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(this::sendStarterMessage)
                .reply(masterReplyFlow.welcome())
                .build();
    }

    private void sendStarterMessage(MessageContext messageContext) {
        messageContext.bot().silent().execute(
                ButtonsUtils.buildMessageWithKeyboard(messageContext.chatId(), Buttons.ON_START_NEW_USER));
    }

}
