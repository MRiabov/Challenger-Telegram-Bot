package edu.mriabov.challengertelegrambot.repository;

import edu.mriabov.challengertelegrambot.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByTelegramId(long telegramId);

}
