package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByTelegramId(long telegramId);

    Optional<User> getUserByTelegramId(long telegramId);

    Optional<User> getUserByUsername(String username);

    @Query("SELECT c FROM User u JOIN u.chatList c WHERE u.telegramId=:chatID")
    Page<Chat> findChatsByTelegramId(long chatID, Pageable pageable);

    @Query("SELECT c1 FROM User u1 JOIN u1.chatList c1 WHERE c1 IN " +
            "(SELECT c2 FROM User u2 JOIN u2.chatList c2 WHERE u2.telegramId=:chatID2) " +
            "AND u1.telegramId=:chatID1 " +
            "ORDER BY c1.telegramID")
    Page<Chat> findMatchingChatsFor2Users(long chatID1, long chatID2, Pageable pageable);
}
