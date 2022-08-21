package edu.mriabov.challengertelegrambot.dialogs.command;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class StartCommand implements AbilityExtension {

    private final TelegramBot telegramBot;
    private final Buttons buttons;
    public Ability onStart(){
         SendMessage message = new SendMessage("1","1");
        message.setReplyMarkup(TelegramUtils.ArrayToReplyMarkup(Buttons.MAIN_MENU.getKeyboard()));
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .reply(ReplyFlow.builder(telegramBot.db())
                        .action((baseAbilityBot, update) -> telegramBot.execute(buttons.buildSendMessageWithKeyboard(update.getMessage().getChatId(),Buttons.ON_START_NEW_USER)))


                        .build()))


        .build();
    }

}
