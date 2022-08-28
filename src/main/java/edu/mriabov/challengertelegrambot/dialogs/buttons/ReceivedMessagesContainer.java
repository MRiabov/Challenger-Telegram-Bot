package edu.mriabov.challengertelegrambot.dialogs.buttons;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceivedMessagesContainer {



    public Buttons getByText(String text){
        return receivedMessagesMap.get(text);
    }

    private static Map<String, Buttons> fillMap(){
        return Arrays.stream(values())
                .collect(Collectors.toUnmodifiableMap(receivedMessages -> receivedMessages.receivedMessage, receivedMessages -> receivedMessages.nextInvocation));
    }
}
