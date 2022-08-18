package edu.mriabov.challengertelegrambot.repository;

import edu.mriabov.challengertelegrambot.repository.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {
}
