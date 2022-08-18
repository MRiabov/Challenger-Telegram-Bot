package edu.mriabov.challengertelegrambot.repository;

import edu.mriabov.challengertelegrambot.repository.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Challenge,Integer> {
}
