package edu.mriabov.challengertelegrambot.dao.service.impl;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.UserStatsRepository;
import edu.mriabov.challengertelegrambot.dao.service.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatsServiceImpl implements UserStatsService {

    private final UserStatsRepository userStatsRepository;

    @Override
    public UserStats create() {
        return userStatsRepository.save(new UserStats());
    }

    @Override
    public int incrementStats(long userID, Area area, Difficulty difficulty) {
        return 0;//todo
    }
}
