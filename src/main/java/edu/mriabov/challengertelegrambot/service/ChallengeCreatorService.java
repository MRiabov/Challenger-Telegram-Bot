package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.User;

public interface ChallengeCreatorService {

    void fillChatPageCache(long chatID);

    void fillUserPageCache(long chatID);

    boolean selectUsers(long chatID, User user);

    boolean selectChats(long chatID, int selectedNumber);

    boolean selectDifficulty(long chatID, Difficulty difficulty);

    boolean selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long chatID, String username);


}
