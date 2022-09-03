package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean existsByTelegramId(long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    @Override
    public User getUserByTelegramId(long telegramId) {
        return userRepository.getUserByTelegramId(telegramId);

    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public int countChatsById(long chatID) {
        return userRepository.countChatsById(chatID);
    }

    @Override
    public Page<Chat> findAllByTelegramId(long chatID, int page) {
        return userRepository.findAllByTelegramId(chatID,
                Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE).withPage(page));
    }

    @Override
    public long selectByNumber(long chatID, int page) {
        return 0;
    }

    @Override
    public Page<Chat> findAllByPageable(long chatID, Pageable pageable) {
        return userRepository.findAllByTelegramId(chatID,pageable);
    }


}
