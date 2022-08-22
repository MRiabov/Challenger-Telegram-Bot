package edu.mriabov.challengertelegrambot.dialogs.buttons;

import edu.mriabov.challengertelegrambot.service.TelegramBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Slf4j
public enum ReceivedMessages {
    //here you receive messages, and decide what to do next
    MENU_CHALLENGES(ButtonsMessages.MENU_CHALLENGES.getTxt(), Buttons.MY_CHALLENGES),

    ON_START_NEW_USER_YES(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_NO(ButtonsMessages.ON_START_NEW_USER_FIRST_YES.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_FIRST_YES(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(), Buttons.ON_START_NEW_USER_HELP_FIRST),
    ON_START_NEW_USER_FIRST_NO(ButtonsMessages.ON_START_NEW_USER_SECOND_YES.getTxt(), Buttons.MAIN_MENU),

    ON_START_NEW_USER_SECOND_FINISH(ButtonsMessages.ON_START_NEW_USER_THIRD.getTxt(), Buttons.MAIN_MENU)

    //!
    ;
    //!
    private final String text;
    //this is for whatever comes after this button is pressed. should ease development greatly.
    private final Buttons nextInvocation;

    public static ReplyFlow buildFlow(ReceivedMessages receivedMessages,TelegramBot telegramBot) {

        return ReplyFlow.builder(telegramBot.db())
                .onlyIf(update -> update.getMessage().getText().equals(receivedMessages.getText()))
                .action((baseAbilityBot, update) -> {
                    try {
                        telegramBot.execute(Buttons.buildSendMessageWithKeyboard(
                                update.getMessage().getChatId(),receivedMessages.getNextInvocation()
                        ));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }

    public static Optional<ReceivedMessages> getByText(String text) {
        return Arrays.stream(values())
                .filter(receivedMessages -> receivedMessages.text.equals(text))
                .findFirst();
    }
}
