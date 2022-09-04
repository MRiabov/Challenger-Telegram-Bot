package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    boolean existsByTelegramId(long telegramId);

    User getUserByTelegramId(long telegramId);

    Optional<User> getUserByUsername(String username);

    Page<Chat> findAllByTelegramId(long chatID, int page);

    Page<Chat> findAllByPageable(long chatID, Pageable pageable);

    Page<Chat> findMatchingChats(long chatID1, long chatID2);
}
