package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import java.util.List;
@RequiredArgsConstructor
public class ReplyBuilderServiceImpl implements ReplyBuilderService {

    private final TelegramBot telegramBot;

    @Override
    public ReplyFlow buildFlow(ReceivedMessages receivedMessages, List<ReplyFlow> next) {
        return ReplyUtils.buildFlow(receivedMessages, next, telegramBot);
    }

    @Override
    public ReplyFlow buildFlow(ReceivedMessages receivedMessages, ReplyFlow nextFlow) {
        return ReplyUtils.buildFlow(receivedMessages,List.of(nextFlow),telegramBot);
    }
}
