package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils.PAGE_SIZE;

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
    public Page<Chat> findChatsByTelegramId(long chatID, int page) {
        return userRepository.findChatsByTelegramId(chatID,
                Pageable.ofSize(PAGE_SIZE).withPage(page));
    }

    @Override
    public Page<Chat> findChatsByPageable(long chatID, Pageable pageable) {
        return userRepository.findChatsByTelegramId(chatID,pageable);
    }

    @Override
    public Page<Chat> findMatchingChats(long chatID1, long chatID2) {
        return userRepository.findMatchingChatsFor2Users(chatID1,chatID2,Pageable.ofSize(PAGE_SIZE));
    }


}
