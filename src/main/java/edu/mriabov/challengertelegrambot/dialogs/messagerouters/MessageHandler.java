package edu.mriabov.challengertelegrambot.dialogs.messagerouters;

import org.telegram.telegrambots.meta.api.objects.Update;

public sealed interface MessageHandler
        permits MasterMessageHandler, CommandHandler, SubMessageHandler{

    void routeMessages(Update update);

}
