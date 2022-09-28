package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BillingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BillingServiceImplTest {
    @Autowired
    private BillingServiceImpl billingServiceImpl;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link BillingServiceImpl#billCoins(long, int)}
     */
    @Test
    void testBillCoins() {
        // Arrange
        UserStats userStats = fillUserStats();

        User user = fillUser(userStats);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        // Act and Assert
        assertFalse(billingServiceImpl.billCoins(1L, 1));
        verify(userService).getUserByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link BillingServiceImpl#billCoins(long, int)}
     */
    @Test
    void NotEnoughCoinsToBill() {
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        user.setCoins(1);
        long userID = 0L;
        int price = 1000;

        // Act and Assert
        assertThat(this.billingServiceImpl.billCoins(userID, price)).isFalse();
    }

    /**
     * Method under test: {@link BillingServiceImpl#billCoins(long, int)}
     */
    @Test
    void noUserPassed() {
        // Arrange
        when(userService.getUserByTelegramId(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertFalse(billingServiceImpl.billCoins(1L, 1));
        verify(userService).getUserByTelegramId(anyLong());
    }

    /**
     * Method under test: {@link BillingServiceImpl#billCoins(long, int)}
     */
    @Test
    void freeChallengeDoesntChangeValuesAndReturnsTrue() {
        // Arrange
        UserStats userStats = fillUserStats();
        User user = fillUser(userStats);
        user.setCoins(1234);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userService).save((User) any());
        when(userService.getUserByTelegramId(anyLong())).thenReturn(ofResult);

        // Act and Assert
        assertTrue(billingServiceImpl.billCoins(1L, 0));
        verify(userService, atLeast(1)).getUserByTelegramId(anyLong());
        verify(userService).save((User) any());
        assertEquals(1234,user.getCoins());
    }

    @NotNull
    private static UserStats fillUserStats() {
        UserStats userStats = new UserStats();
        userStats.setFinances(1);
        userStats.setFitness(1);
        userStats.setId(1);
        userStats.setMindfulness(1);
        userStats.setRelationships(1);
        return userStats;
    }

    @NotNull
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
}

