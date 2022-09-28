package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserStatsService;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.service.SenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RegistrationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RegistrationServiceImplTest {
    @MockBean
    private GroupService groupService;

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @MockBean
    private SenderService senderService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserStatsService userStatsService;

    /**
     * Method under test: {@link RegistrationServiceImpl#registerUser(User)}
     */
    @Test
    void testRegisterUser() {
        // Arrange
        doNothing().when(userService).save((edu.mriabov.challengertelegrambot.dao.model.User) any());

        UserStats userStats = new UserStats();
        userStats.setFinances(1);
        userStats.setFitness(1);
        userStats.setId(1);
        userStats.setMindfulness(1);
        userStats.setRelationships(1);
        when(userStatsService.create()).thenReturn(userStats);

        // Act
        registrationServiceImpl.registerUser(new org.telegram.telegrambots.meta.api.objects.User());

        // Assert
        verify(userService).save((edu.mriabov.challengertelegrambot.dao.model.User) any());
        verify(userStatsService).create();
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#registerChat(Update)}
     */
    @Test
    void testRegisterChat4() {
        // Arrange
        when(groupService.save((Group) any())).thenReturn(true);

        Chat chat = new Chat();
        chat.setId(123L);

        ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();
        chatMemberUpdated.setChat(chat);

        Update update = new Update();
        update.setMyChatMember(chatMemberUpdated);

        // Act
        registrationServiceImpl.registerChat(update);

        // Assert
        verify(groupService).save((Group) any());
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#linkUserToGroup(long, long)}
     */
    @Test
    void testLinkUserToGroup() {
        // Arrange
        when(userService.addChat(anyLong(), (Group) any())).thenReturn(true);

        Group group = fillGroup();
        when(groupService.findByTelegramID(anyLong())).thenReturn(group);
        doNothing().when(senderService).sendMessages(anyLong(), (String) any());

        // Act
        registrationServiceImpl.linkUserToGroup(1L, 1L);

        // Assert
        verify(userService).addChat(anyLong(), (Group) any());
        verify(groupService).findByTelegramID(anyLong());
        verify(senderService).sendMessages(anyLong(), (String) any());
    }

    private static Group fillGroup() {
        Group group = new Group();
        group.setChallenges(new HashSet<>());
        group.setGroupName("Group Name");
        group.setId(1);
        group.setTelegramId(123L);
        group.setTotalTasksCompleted(1);
        group.setUsers(new HashSet<>());
        return group;
    }

    /**
     * Method under test: {@link RegistrationServiceImpl#linkUserToGroup(long, long)}
     */
    @Test
    void testLinkUserToGroup2() {
        // Arrange
        when(userService.addChat(anyLong(), (Group) any())).thenReturn(false);

        Group group = fillGroup();
        when(groupService.findByTelegramID(anyLong())).thenReturn(group);
        doNothing().when(senderService).sendMessages(anyLong(), (String) any());

        // Act
        registrationServiceImpl.linkUserToGroup(1L, 1L);

        // Assert that nothing has changed
        verify(userService).addChat(anyLong(), (Group) any());
        verify(groupService).findByTelegramID(anyLong());
    }
}

