package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.bot.TelegramBot;
import edu.mriabov.challengertelegrambot.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.cache.ChallengePageCache;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.config.BotConfig;
import edu.mriabov.challengertelegrambot.dao.daoservice.ChallengeService;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.daoservice.impl.UserStatsServiceImpl;
import edu.mriabov.challengertelegrambot.dao.repository.UserStatsRepository;
import edu.mriabov.challengertelegrambot.handler.impl.LogicMessagesHandler;
import edu.mriabov.challengertelegrambot.handler.impl.NumpadHandler;
import edu.mriabov.challengertelegrambot.handler.impl.PrivateMasterMessageHandlerImpl;
import edu.mriabov.challengertelegrambot.privatechat.ReceivedMessagesContainer;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import edu.mriabov.challengertelegrambot.service.impl.ChallengeCreatorServiceImpl;
import edu.mriabov.challengertelegrambot.service.impl.DynamicButtonServiceImpl;
import edu.mriabov.challengertelegrambot.service.impl.RegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CreateCustomChallenge.class})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
@Component
class CreateCustomChallengeTest {

    @MockBean
    private ChallengeCache challengeCache;

    @MockBean
    private GroupService groupService;

    @MockBean
    private SenderService senderService;

    @MockBean
    private UserService userService;

    @MockBean
    private ValidatorService validatorService;

    private final CreateCustomChallenge createCustomChallenge;
    private final AbsSender absSender;

    @Test
    void processMessage() {
        Message message = new Message();
        message.setFrom(new User(1234567890L, "test", false));
        message.setText("/custom medium fitness @user do a workout.");
        createCustomChallenge.processMessage(absSender, message,
                new String[]{"medium", "fitness", "@JustZed32", "do", "a", "workout"});
    }

    /**
     * Method under test: {@link CreateCustomChallenge#processMessage(AbsSender, Message, String[])}
     */
    @Test
    void testProcessMessage() {
        // Arrange
        when(validatorService.isNotGroupChat((Message) any())).thenReturn(true);
        ReceivedMessagesContainer receivedMessagesContainer = new ReceivedMessagesContainer();
        UserPageCache userPageCache = new UserPageCache();
        ChatPageCache chatPageCache = new ChatPageCache();
        ChallengeService challengeService = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache, chatPageCache, null, challengeService, senderService);

        ChallengePageCache challengePageCache = new ChallengePageCache();
        LogicMessagesHandler logicMessagesHandler = new LogicMessagesHandler(challengeCreatorService, challengePageCache,
                userService);

        UserPageCache userPageCache1 = new UserPageCache();
        ChatPageCache chatPageCache1 = new ChatPageCache();
        ChallengeService challengeService1 = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService1 = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache1, chatPageCache1, null, challengeService1, senderService);

        ChatPageCache chatPageCache2 = new ChatPageCache();
        UserPageCache userPageCache2 = new UserPageCache();
        ChallengePageCache challengePageCache1 = new ChallengePageCache();
        ChatPageCache chatPageCache3 = new ChatPageCache();
        UserPageCache userPageCache3 = new UserPageCache();
        PrivateMasterMessageHandlerImpl privateMasterMessageHandler = new PrivateMasterMessageHandlerImpl(senderService,
                receivedMessagesContainer, logicMessagesHandler, userService,
                new NumpadHandler(challengeCreatorService1, chatPageCache2, userPageCache2, userService, groupService,
                        challengeCache, challengePageCache1,
                        new DynamicButtonServiceImpl(chatPageCache3, userPageCache3, new ChallengePageCache())));

        BotConfig config = new BotConfig();
        UserStatsServiceImpl userStatsService = new UserStatsServiceImpl(mock(UserStatsRepository.class));
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl(userService, groupService,
                userStatsService, senderService);

        ArrayList<IBotCommand> iBotCommandList = new ArrayList<>();
        TelegramBot absSender = new TelegramBot(privateMasterMessageHandler, config, registrationService,
                iBotCommandList);

        Message message = new Message();

        // Act
        createCustomChallenge.processMessage(absSender, message, new String[]{"Arguments"});

        // Assert that nothing has changed
        verify(validatorService).isNotGroupChat((Message) any());
        assertEquals(iBotCommandList, message.getNewChatMembers());
    }

    /**
     * Method under test: {@link CreateCustomChallenge#processMessage(AbsSender, Message, String[])}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testProcessMessage2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.telegram.telegrambots.meta.api.objects.User.getId()" because the return value of "org.telegram.telegrambots.meta.api.objects.Message.getFrom()" is null
        //       at edu.mriabov.challengertelegrambot.commands.CreateCustomChallenge.processMessage(CreateCustomChallenge.java:52)
        //   In order to prevent processMessage(AbsSender, Message, String[])
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   processMessage(AbsSender, Message, String[]).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        when(validatorService.isNotGroupChat((Message) any())).thenReturn(false);
        ReceivedMessagesContainer receivedMessagesContainer = new ReceivedMessagesContainer();
        UserPageCache userPageCache = new UserPageCache();
        ChatPageCache chatPageCache = new ChatPageCache();
        ChallengeService challengeService = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache, chatPageCache, null, challengeService, senderService);

        ChallengePageCache challengePageCache = new ChallengePageCache();
        LogicMessagesHandler logicMessagesHandler = new LogicMessagesHandler(challengeCreatorService, challengePageCache,
                userService);

        UserPageCache userPageCache1 = new UserPageCache();
        ChatPageCache chatPageCache1 = new ChatPageCache();
        ChallengeService challengeService1 = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService1 = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache1, chatPageCache1, null, challengeService1, senderService);

        ChatPageCache chatPageCache2 = new ChatPageCache();
        UserPageCache userPageCache2 = new UserPageCache();
        ChallengePageCache challengePageCache1 = new ChallengePageCache();
        ChatPageCache chatPageCache3 = new ChatPageCache();
        UserPageCache userPageCache3 = new UserPageCache();
        PrivateMasterMessageHandlerImpl privateMasterMessageHandler = new PrivateMasterMessageHandlerImpl(senderService,
                receivedMessagesContainer, logicMessagesHandler, userService,
                new NumpadHandler(challengeCreatorService1, chatPageCache2, userPageCache2, userService, groupService,
                        challengeCache, challengePageCache1,
                        new DynamicButtonServiceImpl(chatPageCache3, userPageCache3, new ChallengePageCache())));

        BotConfig config = new BotConfig();
        UserStatsServiceImpl userStatsService = new UserStatsServiceImpl(mock(UserStatsRepository.class));
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl(userService, groupService,
                userStatsService, senderService);

        TelegramBot absSender = new TelegramBot(privateMasterMessageHandler, config, registrationService,
                new ArrayList<>());

        // Act
        createCustomChallenge.processMessage(absSender, new Message(), new String[]{"Arguments"});
    }

    /**
     * Method under test: {@link CreateCustomChallenge#processMessage(AbsSender, Message, String[])}
     */
    @Test
    void testProcessMessage3() {
        // Arrange
        when(validatorService.isNotGroupChat((Message) any())).thenThrow(new IllegalArgumentException("foo"));
        ReceivedMessagesContainer receivedMessagesContainer = new ReceivedMessagesContainer();
        UserPageCache userPageCache = new UserPageCache();
        ChatPageCache chatPageCache = new ChatPageCache();
        ChallengeService challengeService = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache, chatPageCache, null, challengeService, senderService);

        ChallengePageCache challengePageCache = new ChallengePageCache();
        LogicMessagesHandler logicMessagesHandler = new LogicMessagesHandler(challengeCreatorService, challengePageCache,
                userService);

        UserPageCache userPageCache1 = new UserPageCache();
        ChatPageCache chatPageCache1 = new ChatPageCache();
        ChallengeService challengeService1 = mock(ChallengeService.class);
        ChallengeCreatorServiceImpl challengeCreatorService1 = new ChallengeCreatorServiceImpl(userService, groupService,
                challengeCache, userPageCache1, chatPageCache1, null, challengeService1, senderService);

        ChatPageCache chatPageCache2 = new ChatPageCache();
        UserPageCache userPageCache2 = new UserPageCache();
        ChallengePageCache challengePageCache1 = new ChallengePageCache();
        ChatPageCache chatPageCache3 = new ChatPageCache();
        UserPageCache userPageCache3 = new UserPageCache();
        PrivateMasterMessageHandlerImpl privateMasterMessageHandler = new PrivateMasterMessageHandlerImpl(senderService,
                receivedMessagesContainer, logicMessagesHandler, userService,
                new NumpadHandler(challengeCreatorService1, chatPageCache2, userPageCache2, userService, groupService,
                        challengeCache, challengePageCache1,
                        new DynamicButtonServiceImpl(chatPageCache3, userPageCache3, new ChallengePageCache())));

        BotConfig config = new BotConfig();
        UserStatsServiceImpl userStatsService = new UserStatsServiceImpl(mock(UserStatsRepository.class));
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl(userService, groupService,
                userStatsService, senderService);

        TelegramBot absSender = new TelegramBot(privateMasterMessageHandler, config, registrationService,
                new ArrayList<>());

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> createCustomChallenge.processMessage(absSender, new Message(), new String[]{"Arguments"}));
        verify(validatorService).isNotGroupChat((Message) any());
    }
}