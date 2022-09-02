package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;

public interface ChallengeCreatorService {

    boolean selectUsers(long chatID, int selectedNumber);

    void selectChats(long chatID, int selectedNumber);

    boolean selectDifficulty(long chatID, Difficulty difficulty);

    boolean selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long chatID, String username);


}
