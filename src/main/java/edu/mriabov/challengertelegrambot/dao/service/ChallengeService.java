package edu.mriabov.challengertelegrambot.dao.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeService {

    void save(Challenge challenge);
    Page<User> findUsers(int userID, Pageable pageable);

}
