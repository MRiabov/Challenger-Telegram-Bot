package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface ReplyBuilderService {

   ReplyFlow buildSimpleFlow(int id,@NotNull ReceivedMessages receivedMessages, @NotNull @NotEmpty List<Reply> nextFlow);
   ReplyFlow buildSimpleFlow(int id,@NotNull ReceivedMessages receivedMessages, @NotNull @NotEmpty Reply nextFlow);
   Reply buildSimpleReply(@NotNull ReceivedMessages receivedMessages);

   Reply cancelMessage(@NotNull Buttons buttons);


}
