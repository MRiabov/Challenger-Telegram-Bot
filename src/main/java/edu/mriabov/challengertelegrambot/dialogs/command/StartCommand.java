package edu.mriabov.challengertelegrambot.dialogs.command;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class StartCommand implements AbilityExtension {

    private final TelegramBot telegramBot;

    public Ability onStart(){
        return Ability.builder()
                .name("start")
                .info("(re)Starts the bot.")
                .action(ctx -> {
                    try {
                        telegramBot.sender().execute(new SendMessage(
                                ctx.chatId().toString(),Buttons.MAIN_MENU.getMessage()))
                                .
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }).build();
    }

}
