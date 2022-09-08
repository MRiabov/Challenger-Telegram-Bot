package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;

import java.util.Optional;

public interface ChallengeCreatorService {

    void fillChatPageCache(long userID);

    void fillUserPageCache(long userID, Group group);

    boolean selectUsers(long thisUserID, User otherUser);

    Optional<Group> selectChats(long userID, int selectedNumber);

    boolean setDescription(long userID, String message);

    boolean selectDifficulty(long chatID, Difficulty difficulty);

    boolean selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long userID, String username);

    Group getSelectedGroupID(long userID);

    boolean confirm(long userID);


}
