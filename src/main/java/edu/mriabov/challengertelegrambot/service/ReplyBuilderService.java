package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface ReplyBuilderService {

   ReplyFlow buildFlow(@NotNull ReceivedMessages receivedMessages, @NotNull List<ReplyFlow> nextFlow);
   ReplyFlow buildFlow(@NotNull ReceivedMessages receivedMessages, @NotNull ReplyFlow nextFlow);

}
