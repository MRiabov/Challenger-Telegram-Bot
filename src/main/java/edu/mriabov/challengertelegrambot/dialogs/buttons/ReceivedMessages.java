package edu.mriabov.challengertelegrambot.dialogs.buttons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Slf4j
public enum ReceivedMessages {
    //here you receive messages, and, based on the answer, decide what to do next
    //= messages to catch and decide the next action
    //meta
    MAIN_MENU(ButtonsMessages.MAIN_MENU.getText(), Buttons.MAIN_MENU),
    CANCEL(ButtonsMessages.CANCEL.getText(), null /*intended! should never be used. I hope.*/),
    //main menu
    MENU_MY_CHALLENGES(ButtonsMessages.MENU_MY_CHALLENGES.getText(), Buttons.MENU_MY_CHALLENGES),
    MENU_CHALLENGE_YOUR_FRIENDS(ButtonsMessages.MENU_CHALLENGE_YOUR_FRIENDS.getText(), Buttons.MENU_CHALLENGE_YOUR_FRIENDS),
    MENU_REST(ButtonsMessages.MENU_REST.getText(), Buttons.MENU_MY_CHALLENGES),//TODO rest!!!
    MENU_FAQ(ButtonsMessages.MENU_FAQ.getText(), Buttons.MENU_FAQ),
    //my challenges
    MARK_CHALLENGE_AS_COMPLETED(ButtonsMessages.MARK_CHALLENGE_AS_COMPLETED_MENU.getText(),Buttons.MARK_CHALLENGE_AS_COMPLETED),
    SET_GOAL(ButtonsMessages.SET_GOAL.getText(), Buttons.SET_GOAL),
    SKIP_CHALLENGES(ButtonsMessages.SKIP_CHALLENGE.getText(), Buttons.SKIP_CHALLENGES),
    //start
    ON_START_NEW_USER_YES(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getText(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_NO(ButtonsMessages.ON_START_NEW_USER_FIRST_NO.getText(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_FIRST_YES(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getText(), Buttons.ON_START_NEW_USER_HELP_SECOND),
    ON_START_NEW_USER_FIRST_NO(ButtonsMessages.ON_START_NEW_USER_SECOND_NO.getText(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_SECOND_FINISH(ButtonsMessages.ON_START_NEW_USER_THIRD.getText(), Buttons.MAIN_MENU)


    //!
    ;
    //!

    private final String receivedMessage;
    //this is for whatever comes after this button is pressed. should ease development greatly.
    private final Buttons nextInvocation;
    private final Map<String,Buttons> receivedMessagesMap;

    ReceivedMessages(String receivedMessage, Buttons nextInvocation) {
        this.receivedMessage = receivedMessage;
        this.nextInvocation = nextInvocation;
        receivedMessagesMap= fillMap();
    }

    public Buttons getByText(String text){
        return receivedMessagesMap.get(text);
    }

    private Map<String, Buttons> fillMap(){
        return Arrays.stream(values())
                .collect(Collectors.toUnmodifiableMap(receivedMessages -> receivedMessages.receivedMessage,receivedMessages -> receivedMessages.nextInvocation));
    }

}
