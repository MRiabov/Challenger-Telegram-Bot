package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

    @Query("SELECT u FROM Challenge c JOIN c.users u WHERE c.id=:id")
    Page<User> findUsersById(int id, Pageable pageable);

    @Query("SELECT u FROM Challenge c JOIN c.users u WHERE c.id=:id")
    List<User> findUsersById(int id);

    Set<Challenge> findByExpiresAtBetween(Instant expiresAt, Instant expiresAt2);
}
