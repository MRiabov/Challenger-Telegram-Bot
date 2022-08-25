package edu.mriabov.challengertelegrambot.utils;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.dialogs.buttons.PublicButtonsMessages;
import edu.mriabov.challengertelegrambot.dialogs.buttons.ReceivedMessages;
import edu.mriabov.challengertelegrambot.service.TelegramBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class ReplyUtils {

    private ReplyUtils() {
    }

    public static ReplyFlow buildSimpleFlow(int id, ReceivedMessages receivedMessages, List<Reply> next) {
        ReplyFlow.ReplyFlowBuilder builder = ReplyFlow.builder(TelegramBot.database, id);
        builder.onlyIf(
                update -> update.getMessage().getText().equals(receivedMessages.getReceivedMessage())
                        || update.getMessage().getText().equals(PublicButtonsMessages.CANCEL.getText()));
        builder.action((baseAbilityBot, update) -> execute(receivedMessages, baseAbilityBot, update));
        for (Reply nextReply : next) builder.next(nextReply);
        return builder.build();
    }

    public static Reply buildSimpleReply(ReceivedMessages receivedMessages) {
        return Reply.of(
                (baseAbilityBot, update) -> sendKeyboard(update, baseAbilityBot, receivedMessages.getNextInvocation()),
                update -> update.getMessage().getText().equals(receivedMessages.getReceivedMessage())
        );
    }

    public static Reply buildCancelReply(Buttons buttons){
        return Reply.of(
                (baseAbilityBot, update) -> sendKeyboard(update,baseAbilityBot,buttons),
                TelegramUtils::isCancel);
    }

    public static void sendKeyboard(Update update, BaseAbilityBot baseAbilityBot, Buttons buttons) {
        baseAbilityBot.silent().execute(ButtonsUtils.buildMessageWithKeyboard(update.getMessage().getChatId(), buttons));
    }

    private static void execute(ReceivedMessages receivedMessages, BaseAbilityBot baseAbilityBot, Update update) {
        baseAbilityBot.silent().execute(ButtonsUtils.buildMessageWithKeyboard(
                update.getMessage().getChatId(), receivedMessages.getNextInvocation()
        ));
    }


}
