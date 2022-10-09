package edu.mriabov.challengertelegrambot.handler.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PrivateMasterMessageHandlerImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PrivateMasterMessageHandlerImplTest {
    @MockBean
    private LogicMessagesHandler logicMessagesHandler;

    @MockBean
    private NumpadHandler numpadHandler;

    @Autowired
    private PrivateMasterMessageHandlerImpl privateMasterMessageHandlerImpl;

    @MockBean
    private ReceivedMessagesContainer receivedMessagesContainer;

    @MockBean
    private SenderService senderService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        doNothing().when(senderService).sendMessages(anyLong(), anyString());
        doNothing().when(senderService).sendMessages(anyLong(), (Buttons) any());
    }

    /**
     * Method under test: {@link PrivateMasterMessageHandlerImpl#handleMessages(Update)}
     */
    @Test
    void testHandleMessages() {
        // Arrange
        Update update = createUpdate("@MRiabov", 123123);

        // Act
        this.privateMasterMessageHandlerImpl.handleMessages(update);

        // Assert
        verify(logicMessagesHandler).handleUsernames(update.getMessage().getChatId(), "@MRiabov");
    }

    @Test
    void handleDefaultButtons() {
        //Arrange
        Update update = createUpdate("\uD83D\uDCAA My Challenges", 1123123L);
        when(userService.existsByTelegramId(1123123L)).thenReturn(true);
        //Act
        privateMasterMessageHandlerImpl.handleMessages(update);
        //Assert
        verify(receivedMessagesContainer).getByText("\uD83D\uDCAA My Challenges");
    }

    @Test
    void handleNumpad() {
        //Arrange
        String messageText = "1️⃣ " + Appendix.CHAT_APPENDIX.getText();
        long chatID = 123123;
        Update update = createUpdate(messageText, chatID);

        when(userService.existsByTelegramId(chatID)).thenReturn(true);
        //Act
        privateMasterMessageHandlerImpl.handleMessages(update);
        //Assert
        verify(numpadHandler).handleMessages(chatID, messageText);
    }

    @Test
    void handleDescription() {
        //Arrange
        String messageText = "gosh, there are 40 symbols in this text.";
        long chatID = 123123;
        Update update = createUpdate(messageText, chatID);
        when(userService.existsByTelegramId(1123123L)).thenReturn(true);
        //Act
        privateMasterMessageHandlerImpl.handleMessages(update);
        //Assert
        verify(logicMessagesHandler).setMessage(chatID, messageText);
    }


    @NotNull
    private static Update createUpdate(String messageText, long chatID) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(chatID);
        message.setChat(chat);
        message.setText(messageText);
        update.setMessage(message);
        return update;
    }

}

