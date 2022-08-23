package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.service.TelegramBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.abilitybots.api.objects.ReplyFlow;

@Getter
@RequiredArgsConstructor
public class MainMenuFlow {

    private final TelegramBot telegramBot;

    public ReplyFlow getFlow(){
        return ReplyFlow.builder(telegramBot.db())


                .build();
    }
}
