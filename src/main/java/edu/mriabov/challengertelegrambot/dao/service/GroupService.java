package edu.mriabov.challengertelegrambot.dao.service;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface GroupService {

    Page<User> findUsersByTelegramID(long userID, long groupID, int page);

    Page<User> findUsersByPageable(long userID, long groupID, Pageable pageable);

    Set<User> findAllUsers(long groupID);

    boolean save(Group group);

    Group findByTelegramID(long groupID);

    boolean isUserAdmin();

}
