package edu.mriabov.challengertelegrambot.dao.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChallengeRepository;
import edu.mriabov.challengertelegrambot.dao.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public void save(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    @Override
    public Page<User> findUsers(int id, Pageable pageable) {
        return challengeRepository.findUsersById(id,pageable);
    }

}
