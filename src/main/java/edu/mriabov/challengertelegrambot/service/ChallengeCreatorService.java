package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;

import java.util.Optional;

public interface ChallengeCreatorService {

    void fillChatPageCache(long chatID);

    void fillUserPageCache(long userID, Chat group);

    boolean selectUsers(long chatID, User user);

    Optional<Chat> selectChats(long chatID, int selectedNumber);

    boolean selectDifficulty(long chatID, Difficulty difficulty);

    boolean selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long chatID, String username);

    Chat getSelectedGroupID(long userID);


}
