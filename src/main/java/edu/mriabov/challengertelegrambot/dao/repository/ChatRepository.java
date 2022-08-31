package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {

}
