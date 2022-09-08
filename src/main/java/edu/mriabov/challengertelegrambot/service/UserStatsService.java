package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;

public interface UserStatsService {

    UserStats create();

    void incrementStats(long userID, Area area, Difficulty difficulty);
//todo maybe return int as a currentAmount. dunno though
}
