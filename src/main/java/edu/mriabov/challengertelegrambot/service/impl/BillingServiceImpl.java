package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.dao.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserService userService;

    @Override
    public boolean isEnoughCoins(long userID, int price) {
        Optional<User> user = userService.getUserByTelegramId(userID);
        if (user.isEmpty()) return false;
        return user.get().getCoins() > price;
    }

    @Override
    public List<Boolean> isEnoughCoins(long userID, int[] prices) {
        List<Boolean> result = new ArrayList<>();
        Optional<User> user = userService.getUserByTelegramId(userID);

        for (int price : prices)
            user.ifPresentOrElse(value -> result.add(value.getCoins() > price), () -> result.add(false));
        return result;
    }

    @Override
    public boolean billCoins(long userID, int price) {
        if (!isEnoughCoins(userID, price)) return false;
        Optional<User> userOptional = userService.getUserByTelegramId(userID);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        user.setCoins(user.getCoins() - price);
        userService.save(user);
        return true;
    }

    @Override
    public int challengePrice(Challenge challenge) {
        return challenge.getUsers().size() * challenge.getDifficulty().price;
    }
}
