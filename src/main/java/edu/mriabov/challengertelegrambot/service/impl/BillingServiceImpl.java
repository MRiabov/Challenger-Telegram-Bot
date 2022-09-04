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

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserService userService;
    private final SenderService senderService;

    @Override
    public boolean isEnoughCoins(long chatID, int price) {
        return userService.getUserByTelegramId(chatID).getCoins() > price;
    }

    @Override
    public List<Boolean> isEnoughCoins(long chatID, int[] prices) {
        List<Boolean> result = new ArrayList<>();
        User user = userService.getUserByTelegramId(chatID);
        for (int price : prices) result.add(user.getCoins() > price);
        return result;
    }

    @Override
    public boolean billCoins(long chatID, int price) {
        User user = userService.getUserByTelegramId(chatID);
            if (isEnoughCoins(chatID, price)) {
                user.setCoins(user.getCoins() - price);
                return true;
            } else senderService.sendMessages(chatID, Buttons.NEED_MORE_COINS);
        return false;
    }
}
