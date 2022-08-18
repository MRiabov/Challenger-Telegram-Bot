package edu.mriabov.challengertelegrambot.repository;

import edu.mriabov.challengertelegrambot.repository.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
