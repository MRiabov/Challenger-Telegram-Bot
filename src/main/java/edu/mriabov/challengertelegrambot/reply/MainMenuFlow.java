package edu.mriabov.challengertelegrambot.reply;

import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.Getter;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;

@Getter

public class MainMenuFlow {

    private final ReplyFlow flow;
    private final TelegramBot telegramBot;


}
