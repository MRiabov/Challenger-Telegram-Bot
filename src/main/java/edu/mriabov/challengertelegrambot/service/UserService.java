package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean existsByTelegramId(long telegramId);

    Optional<User> getUserByTelegramId(long telegramId);

    Optional<User> getUserByUsername(String username);

    Page<Group> findChatsByTelegramId(long chatID, int page);

    Page<Group> findChatsByPageable(long chatID, Pageable pageable);

    Page<Group> findMatchingChats(long chatID1, long chatID2);

    boolean addChat(long userID, Group group);

    void save(User user);

    List<Challenge> findAllChallenges(long userID);
}
