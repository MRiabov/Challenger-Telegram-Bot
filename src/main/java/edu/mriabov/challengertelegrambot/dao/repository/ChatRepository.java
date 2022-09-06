package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query("SELECT u FROM Chat c JOIN c.users u WHERE c.telegramID=:groupID AND u.telegramId<>:userID")
    Page<User> findUsersByTelegramID(long userID, long groupID, Pageable pageable);

    boolean existsByTelegramID(long chatID);

    Chat findByTelegramID(long chatID);

}
