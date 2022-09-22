package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;

import java.util.Optional;

public interface ChallengeCreatorService {

    void fillChatPageCache(long userID);

    void fillUserPageCache(long userID, Group group);

    void selectUsers(long thisUserID, User otherUser);

    Optional<Group> selectChats(long userID, int selectedNumber);

    void setDescription(long userID, String message);

    void selectDifficulty(long chatID, Difficulty difficulty);

    void selectArea(long chatID, Area area);

    boolean selectUsersByUsername(long userID, String username);

    Group getSelectedGroupID(long userID);

    boolean confirm(long userID);

    void selectGoalLength(long userID, int lengthInWeeks);
}
