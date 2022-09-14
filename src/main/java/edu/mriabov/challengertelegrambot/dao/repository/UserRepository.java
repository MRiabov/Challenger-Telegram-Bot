package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByTelegramId(long telegramId);

    Optional<User> getUserByTelegramId(long telegramId);

    Optional<User> getUserByUsername(String username);

    @Query("SELECT c FROM User u JOIN u.groups c WHERE u.telegramId=:userID")
    Set<Group> findChatsByTelegramId(long userID);

    @Query("SELECT c FROM User u JOIN u.groups c WHERE u.telegramId=:userID")
    Page<Group> findChatsByTelegramId(long userID, Pageable pageable);

    @Query("SELECT c1 FROM User u1 JOIN u1.groups c1 WHERE c1 IN " +
            "(SELECT c2 FROM User u2 JOIN u2.groups c2 WHERE u2.telegramId=:chatID2) " +
            "AND u1.telegramId=:chatID1 " +
            "ORDER BY c1.telegramId")
    Page<Group> findMatchingChatsFor2Users(long chatID1, long chatID2, Pageable pageable);

    @Query("SELECT c FROM User u JOIN u.challenges c WHERE u.telegramId=:userID ORDER BY c.expiresAt")
    Page<Challenge> findAllChallengesByTelegramId(long userID,Pageable pageable);

    @Query("SELECT c FROM User u JOIN u.challenges c WHERE c.id<>:challengeID AND u=:userID")
    Set<Challenge> getAllChallengesButOne(long userID,int challengeID);
}
