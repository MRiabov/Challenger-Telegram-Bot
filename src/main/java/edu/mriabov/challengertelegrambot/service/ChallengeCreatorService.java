package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;

import java.util.Optional;

public interface ChallengeCreatorService {

    void fillChatPageCache(long userID);

    void fillUserPageCache(long userID, Chat group);

    boolean selectUsers(long thisUserID, User otherUser);

    Optional<Chat> selectChats(long userID, int selectedNumber);

    boolean selectDifficulty(long chatID, Difficulty difficulty);

    boolean selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long userID, String username);

    Chat getSelectedGroupID(long userID);

    boolean confirm(long userID);
}
