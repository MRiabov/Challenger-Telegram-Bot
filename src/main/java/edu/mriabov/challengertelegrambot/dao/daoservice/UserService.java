package edu.mriabov.challengertelegrambot.dao.daoservice;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    boolean existsByTelegramId(long telegramId);

    Optional<User> getUserByTelegramId(long telegramId);

    Optional<User> getUserByUsername(String username);

    Page<Group> findChatsByTelegramId(long chatID, int page);

    Page<Group> findChatsByPageable(long chatID, Pageable pageable);

    Page<Group> findMatchingChats(long chatID1, long chatID2);

    boolean addChat(long userID, Group group);

    void save(User user);

    void completeChallenge(long userID, Challenge challenge);

    void skipChallenge(long userID, Challenge challenge);

    Page<Challenge> findChallengesByTelegramID(long userID, Pageable pageable);
    Set<Challenge> findAllGoals(long userID);
}
