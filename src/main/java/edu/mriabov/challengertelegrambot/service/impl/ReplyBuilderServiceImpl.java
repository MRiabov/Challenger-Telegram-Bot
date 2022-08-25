package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.ReplyBuilderService;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyBuilderServiceImpl implements ReplyBuilderService {

    @Override
    public ReplyFlow buildSimpleFlow(int id,ReceivedMessages receivedMessages, List<Reply> next) {
        return ReplyUtils.buildSimpleFlow(id,receivedMessages, next);
    }

    @Override
    public ReplyFlow buildSimpleFlow(int id,ReceivedMessages receivedMessages, Reply nextFlow) {
        return ReplyUtils.buildSimpleFlow(id,receivedMessages,List.of(nextFlow));
    }

    @Override
    public Reply buildSimpleReply(ReceivedMessages receivedMessages) {
        return ReplyUtils.buildSimpleReply(receivedMessages);
    }

    @Override
    public Reply cancelMessage(Buttons buttons) {
        return ReplyUtils.buildCancelReply(buttons);
    }


}
