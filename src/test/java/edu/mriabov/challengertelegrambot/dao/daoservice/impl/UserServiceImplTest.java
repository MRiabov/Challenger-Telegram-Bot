package edu.mriabov.challengertelegrambot.dao.daoservice.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#existsByTelegramId(long)}
     */
    private static UserStats fillUserStats() {
        UserStats userStats = new UserStats();
        userStats.setFinances(1);
        userStats.setFitness(1);
        userStats.setId(1);
        userStats.setMindfulness(1);
        userStats.setRelationships(1);
        return userStats;
    }
    private static User fillUser(UserStats userStats) {
        User user = new User();
        user.setChallenges(new HashSet<>());
        user.setCoins(1);
        user.setCreatedChallenges(new HashSet<>());
        user.setFirstName("Jane");
        user.setGroups(new HashSet<>());
        user.setId(1);
        user.setLastName("Doe");
        user.setTelegramId(123L);
        user.setUserStats(userStats);
        user.setUsername("janedoe");
        return user;
    }
    @Test
    void testExistsByTelegramId() {
        when(userRepository.existsByTelegramId(anyLong())).thenReturn(true);
        assertTrue(userServiceImpl.existsByTelegramId(123L));
        verify(userRepository).existsByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#existsByTelegramId(long)}
     */
    @Test
    void testExistsByTelegramId2() {
        when(userRepository.existsByTelegramId(anyLong())).thenReturn(false);
        assertFalse(userServiceImpl.existsByTelegramId(123L));
        verify(userRepository).existsByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByTelegramId(long)}
     */
    @Test
    void testGetUserByTelegramId() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);
        Optional<User> actualUserByTelegramId = userServiceImpl.getUserByTelegramId(123L);
        assertSame(ofResult, actualUserByTelegramId);
        assertTrue(actualUserByTelegramId.isPresent());
        verify(userRepository).getUserByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.getUserByUsername((String) any())).thenReturn(ofResult);
        Optional<User> actualUserByUsername = userServiceImpl.getUserByUsername("janedoe");
        assertSame(ofResult, actualUserByUsername);
        assertTrue(actualUserByUsername.isPresent());
        verify(userRepository).getUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername2() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.getUserByUsername((String) any())).thenReturn(ofResult);
        Optional<User> actualUserByUsername = userServiceImpl.getUserByUsername("@");
        assertSame(ofResult, actualUserByUsername);
        assertTrue(actualUserByUsername.isPresent());
        verify(userRepository).getUserByUsername((String) any());
    }



    /**
     * Method under test: {@link UserServiceImpl#findChatsByTelegramId(long, int)}
     */
    @Test
    void testFindChatsByTelegramId() {
        PageImpl<Group> pageImpl = new PageImpl<>(new ArrayList<>());
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any())).thenReturn(pageImpl);
        Page<Group> actualFindChatsByTelegramIdResult = userServiceImpl.findChatsByTelegramId(1L, 1);
        assertSame(pageImpl, actualFindChatsByTelegramIdResult);
        assertTrue(actualFindChatsByTelegramIdResult.toList().isEmpty());
        verify(userRepository).findChatsByTelegramId(anyLong(), (Pageable) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findChatsByTelegramId(long, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindChatsByTelegramId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Page index must not be less than zero
        //       at org.springframework.data.domain.AbstractPageRequest.<init>(AbstractPageRequest.java:45)
        //       at org.springframework.data.domain.PageRequest.<init>(PageRequest.java:45)
        //       at org.springframework.data.domain.PageRequest.withPage(PageRequest.java:163)
        //       at org.springframework.data.domain.PageRequest.withPage(PageRequest.java:30)
        //       at edu.mriabov.challengertelegrambot.dao.daoservice.impl.UserServiceImpl.findChatsByTelegramId(UserServiceImpl.java:46)
        //   In order to prevent findChatsByTelegramId(long, int)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findChatsByTelegramId(long, int).
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        userServiceImpl.findChatsByTelegramId(1L, -1);
    }

    /**
     * Method under test: {@link UserServiceImpl#findChatsByPageable(long, Pageable)}
     */
    @Test
    void testFindChatsByPageable() {
        PageImpl<Group> pageImpl = new PageImpl<>(new ArrayList<>());
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any())).thenReturn(pageImpl);
        Page<Group> actualFindChatsByPageableResult = userServiceImpl.findChatsByPageable(1L, null);
        assertSame(pageImpl, actualFindChatsByPageableResult);
        assertTrue(actualFindChatsByPageableResult.toList().isEmpty());
        verify(userRepository).findChatsByTelegramId(anyLong(), (Pageable) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findMatchingChats(long, long)}
     */
    @Test
    void testFindMatchingChats() {
        PageImpl<Group> pageImpl = new PageImpl<>(new ArrayList<>());
        when(userRepository.findMatchingChatsFor2Users(anyLong(), anyLong(), (Pageable) any())).thenReturn(pageImpl);
        Page<Group> actualFindMatchingChatsResult = userServiceImpl.findMatchingChats(1L, 1L);
        assertSame(pageImpl, actualFindMatchingChatsResult);
        assertTrue(actualFindMatchingChatsResult.toList().isEmpty());
        verify(userRepository).findMatchingChatsFor2Users(anyLong(), anyLong(), (Pageable) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#addChat(long, Group)}
     */
    @Test
    void testAddChat() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findChatsByTelegramId(anyLong())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(groupRepository.existsByTelegramId(anyLong())).thenReturn(true);

        Group group = fillGroup();
        assertTrue(userServiceImpl.addChat(1L, group));
        verify(userRepository).save((User) any());
        verify(userRepository).getUserByTelegramId(anyLong());
        verify(userRepository).findChatsByTelegramId(anyLong());
        verify(userRepository).findChatsByTelegramId(anyLong(), (Pageable) any());
        verify(groupRepository).existsByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#addChat(long, Group)}
     */
    @Test
    void testAddChat2() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findChatsByTelegramId(anyLong())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(groupRepository.existsByTelegramId(anyLong())).thenReturn(true);

        Group group = fillGroup();
        assertFalse(userServiceImpl.addChat(1L, group));
        verify(userRepository).getUserByTelegramId(anyLong());
        verify(userRepository).findChatsByTelegramId(anyLong(), (Pageable) any());
        verify(groupRepository).existsByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#addChat(long, Group)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddChat3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.data.domain.Page.getContent()" because the return value of "edu.mriabov.challengertelegrambot.dao.repository.UserRepository.findChatsByTelegramId(long, org.springframework.data.domain.Pageable)" is null
        //       at edu.mriabov.challengertelegrambot.dao.daoservice.impl.UserServiceImpl.addChat(UserServiceImpl.java:61)
        //   In order to prevent addChat(long, Group)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   addChat(long, Group).
        //   See https://diff.blue/R013 to resolve this issue.

        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findChatsByTelegramId(anyLong())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any())).thenReturn(null);
        when(groupRepository.existsByTelegramId(anyLong())).thenReturn(true);

        Group group = fillGroup();
        userServiceImpl.addChat(1L, group);
    }

    /**
     * Method under test: {@link UserServiceImpl#addChat(long, Group)}
     */
    @Test
    void testAddChat4() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findChatsByTelegramId(anyLong())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);
        when(userRepository.findChatsByTelegramId(anyLong(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(groupRepository.existsByTelegramId(anyLong())).thenReturn(false);

        Group group = fillGroup();
        assertFalse(userServiceImpl.addChat(1L, group));
        verify(userRepository).findChatsByTelegramId(anyLong(), (Pageable) any());
        verify(groupRepository).existsByTelegramId(anyLong());
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
     * Method under test: {@link UserServiceImpl#isInGroup(long, long)}
     */
    @Test
    void testIsInGroup() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.existsInChat(anyLong(), anyLong())).thenReturn(ofResult);
        assertTrue(userServiceImpl.isInGroup(1L, 1L));
        verify(userRepository).existsInChat(anyLong(), anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#isInGroup(long, long)}
     */
    @Test
    void testIsInGroup2() {
        when(userRepository.existsInChat(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertFalse(userServiceImpl.isInGroup(1L, 1L));
        verify(userRepository).existsInChat(anyLong(), anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#save(User)}
     */
    @Test
    void testSave() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        when(userRepository.save((User) any())).thenReturn(user);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);
        userServiceImpl.save(user1);
        verify(userRepository).save((User) any());
        assertTrue(user1.getChallenges().isEmpty());
        assertEquals("janedoe", user1.getUsername());
        assertSame(userStats1, user1.getUserStats());
        assertEquals(123L, user1.getTelegramId());
        assertEquals(1, user1.getCoins());
        assertEquals("Jane", user1.getFirstName());
        assertTrue(user1.getGroups().isEmpty());
        assertTrue(user1.getCreatedChallenges().isEmpty());
        assertEquals(1, user1.getId());
        assertEquals("Doe", user1.getLastName());
    }

    /**
     * Method under test: {@link UserServiceImpl#completeChallenge(long, Challenge)}
     */
    @Test
    void testCompleteChallenge() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getAllChallengesButOne(anyLong(), anyInt())).thenReturn(new HashSet<>());
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        Group group = fillGroup();
        when(groupRepository.save((Group) any())).thenReturn(group);

        UserStats userStats2 = fillUserStats();

        User user2 = fillUser(userStats2);

        Group group1 = fillGroup();

        Challenge challenge = fillChallenge(user2, group1);
        userServiceImpl.completeChallenge(1L, challenge);
        verify(userRepository).save((User) any());
        verify(userRepository).getUserByTelegramId(anyLong());
        verify(userRepository).getAllChallengesButOne(anyLong(), anyInt());
        verify(groupRepository).save((Group) any());
        assertEquals(2, challenge.getGroup().getTotalTasksCompleted());
    }

    private static Challenge fillChallenge(User user2, Group group1) {
        Challenge challenge = new Challenge();
        challenge.setArea(Area.FINANCES);
        challenge.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setCreatedBy(user2);
        challenge.setDescription("The characteristics of someone or something");
        challenge.setDifficulty(Difficulty.EASY);
        challenge.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        challenge.setFree(true);
        challenge.setGroup(group1);
        challenge.setId(1);
        challenge.setRecurringTime(LocalTime.of(1, 1));
        challenge.setUsers(new HashSet<>());
        return challenge;
    }

    /**
     * Method under test: {@link UserServiceImpl#skipChallenge(long, Challenge)}
     */
    @Test
    void testSkipChallenge() {
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        UserStats userStats1 = fillUserStats();

        User user1 = fillUser(userStats1);

        Group group = fillGroup();

        Challenge challenge = fillChallenge(user1, group);
        assertFalse(userServiceImpl.skipChallenge(1L, challenge));
        verify(userRepository).getUserByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#skipChallenge(long, Challenge)}
     */
    @Test
    void testSkipChallenge2() {
        when(userRepository.getUserByTelegramId(anyLong())).thenReturn(Optional.empty());

        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);

        Group group = fillGroup();

        Challenge challenge = fillChallenge(user, group);
        assertFalse(userServiceImpl.skipChallenge(1L, challenge));
        verify(userRepository).getUserByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link UserServiceImpl#findChallengesByTelegramID(long, Pageable)}
     */
    @Test
    void testFindChallengesByTelegramID() {
        PageImpl<Challenge> pageImpl = new PageImpl<>(new ArrayList<>());
        when(userRepository.findAllChallengesByTelegramId(anyLong(), (Pageable) any())).thenReturn(pageImpl);
        Page<Challenge> actualFindChallengesByTelegramIDResult = userServiceImpl.findChallengesByTelegramID(1L, null);
        assertSame(pageImpl, actualFindChallengesByTelegramIDResult);
        assertTrue(actualFindChallengesByTelegramIDResult.toList().isEmpty());
        verify(userRepository).findAllChallengesByTelegramId(anyLong(), (Pageable) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findAllGoals(long)}
     */
    @Test
    void testFindAllGoals() {
        HashSet<Challenge> challengeSet = new HashSet<>();
        when(userRepository.findChallengesByDifficultyAndTelegramId(anyLong(), (Difficulty) any()))
                .thenReturn(challengeSet);
        Set<Challenge> actualFindAllGoalsResult = userServiceImpl.findAllGoals(1L);
        assertSame(challengeSet, actualFindAllGoalsResult);
        assertTrue(actualFindAllGoalsResult.isEmpty());
        verify(userRepository).findChallengesByDifficultyAndTelegramId(anyLong(), (Difficulty) any());
    }
}

