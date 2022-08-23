package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.telegram.abilitybots.api.objects.Ability;
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
