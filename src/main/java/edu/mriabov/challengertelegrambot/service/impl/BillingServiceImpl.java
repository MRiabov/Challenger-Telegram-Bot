package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserRepository userRepository;
    private final SenderService senderService;

    @Override
    public boolean isEnoughCoins(long chatID, int price) {
        Optional<User> userOptional = userRepository.getUserByTelegramId(chatID);
        if (userOptional.isPresent()) return userOptional.get().getCoins() > price;
        else senderService.userDoesNotExist(chatID);
        return false;
    }

    @Override
    public List<Boolean> isEnoughCoins(long chatID, int[] prices) {
        List<Boolean> result = new ArrayList<>();
        Optional<User> userOptional = userRepository.getUserByTelegramId(chatID);
        if (userOptional.isPresent()) {
            for (int price : prices) result.add(userOptional.get().getCoins() > price);
        } else senderService.userDoesNotExist(chatID);
        return result;
    }

    @Override
    public boolean billCoins(long chatID, int price) {
        Optional<User> userOptional = userRepository.getUserByTelegramId(chatID);
        if (userOptional.isPresent()) {
            if (isEnoughCoins(chatID, price)) {
                userOptional.get().setCoins(userOptional.get().getCoins() - price);
                return true;
            } else senderService.sendMessages(chatID, Buttons.NEED_MORE_COINS);
        } else senderService.userDoesNotExist(chatID);
        return false;
    }
}
