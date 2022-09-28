package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserService userService;

    @Override
    public boolean isEnoughCoins(long userID, int price) {
        Optional<User> user = userService.getUserByTelegramId(userID);
        if (user.isEmpty()) return false;
        return user.get().getCoins() >= price;
    }

    @Override
    public boolean billCoins(long userID, int price) {
        if (!isEnoughCoins(userID, price)) return false;
        Optional<User> userOptional = userService.getUserByTelegramId(userID);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        user.setCoins(user.getCoins() - price);
        userService.save(user);
        log.info("Billed " + price + "coins from user" + user.getFirstName() + " " + user.getLastName());
        return true;
    }

    @Override
    public int challengePrice(Challenge challenge) {
        if (challenge.isFree()) return 0;
        else return challenge.getUsers().size() * challenge.getDifficulty().price;
    }

    @Override
    public boolean isEnoughCoinsForChallenge(Challenge challenge) {
        if (challenge.isFree()) return true;
        return isEnoughCoins(challenge.getCreatedBy().getTelegramId(), challengePrice(challenge));
    }
}
