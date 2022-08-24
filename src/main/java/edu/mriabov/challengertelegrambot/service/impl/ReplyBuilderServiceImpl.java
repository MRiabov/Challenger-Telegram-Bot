package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyBuilderServiceImpl implements ReplyBuilderService {

    @Override
    public ReplyFlow buildSimpleFlow(ReceivedMessages receivedMessages, List<ReplyFlow> next) {
        return ReplyUtils.buildSimpleFlow(receivedMessages, next);
    }

    @Override
    public ReplyFlow buildSimpleFlow(ReceivedMessages receivedMessages, ReplyFlow nextFlow) {
        return ReplyUtils.buildSimpleFlow(receivedMessages,List.of(nextFlow));
    }
}
