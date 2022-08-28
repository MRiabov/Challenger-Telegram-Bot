package edu.mriabov.challengertelegrambot.dialogs.buttons;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceivedMessagesContainer {

    private final Map<String,Buttons> receivedMessagesMap;


    public ReceivedMessagesContainer() {
        this.receivedMessagesMap = fillMap();
    }

    public Buttons getByText(String text){
        return receivedMessagesMap.get(text);
    }

    private static Map<String, Buttons> fillMap(){
        return Arrays.stream(ReceivedMessages.values())
                .collect(Collectors.toUnmodifiableMap(ReceivedMessages::getReceivedMessage, ReceivedMessages::getNextInvocation));
    }
}
