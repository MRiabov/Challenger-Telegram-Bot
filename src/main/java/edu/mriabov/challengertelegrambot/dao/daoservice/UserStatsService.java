package edu.mriabov.challengertelegrambot.dao.daoservice;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;

public interface UserStatsService {

    UserStats create();

    int incrementStats(long userID, Area area, Difficulty difficulty);
//todo maybe return int as a currentAmount. dunno though
}
