package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserService userService;
    private final SenderService senderService;

    @Override
    public boolean isEnoughCoins(long chatID, int price) {
        Optional<User> user = userService.getUserByTelegramId(chatID);
        if (user.isEmpty()) return false;
        return user.get().getCoins() > price;
    }

    @Override
    public List<Boolean> isEnoughCoins(long chatID, int[] prices) {
        List<Boolean> result = new ArrayList<>();
        Optional<User> user = userService.getUserByTelegramId(chatID);

        for (int price : prices)
            user.ifPresentOrElse(value -> result.add(value.getCoins() > price), () -> result.add(false));
        return result;
    }

    @Override
    public boolean billCoins(long chatID, int price) {
        Optional<User> userOptional = userService.getUserByTelegramId(chatID);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        if (isEnoughCoins(chatID, price)) {
            user.setCoins(user.getCoins() - price);
            userService.save(user);
            return true;
        }
        senderService.sendMessages(chatID, Buttons.NEED_MORE_COINS);
        return false;
    }
}
