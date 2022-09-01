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
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByTelegramId(long telegramId);
    Optional<User> getUserByTelegramId(long telegramId);
    Optional<User> getUserByUsername(String username);

    @Query("SELECT COUNT (c) FROM User u JOIN u.chatList c")
    int countChatsById(long chatID);

    @Query("SELECT c FROM User u JOIN u.chatList c")
    Page<Chat> findAllByTelegramId(long chatID, Pageable pageable);

}
