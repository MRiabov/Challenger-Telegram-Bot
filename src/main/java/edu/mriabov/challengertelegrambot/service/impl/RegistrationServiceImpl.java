package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;

    @Override
    public void register(Update update) {
        User user = new User();
        org.telegram.telegrambots.meta.api.objects.User telegramUser = update.getMessage().getFrom();
        user.setCoins(0);
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setTelegramId(telegramUser.getId());
        user.setUsername(telegramUser.getUserName());
        user.setUserStats(new UserStats());
        userRepository.saveAndFlush(user);
    }
}