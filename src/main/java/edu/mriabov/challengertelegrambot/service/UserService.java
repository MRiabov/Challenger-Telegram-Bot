package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    boolean existsByTelegramId(long telegramId);
    Optional<User> getUserByTelegramId(long telegramId);
    Optional<User> getUserByUsername(String username);
    int countChatsById(long chatID);
    Page<Chat> findAllByTelegramId(long chatID,int page);
    long selectByNumber(long chatID,int page);

}
