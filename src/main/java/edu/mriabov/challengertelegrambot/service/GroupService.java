package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GroupService {

    Page<User> findUsersByTelegramID(long userID, long groupID, int page);

    Page<User> findUsersByPageable(long userID, long groupID, Pageable pageable);

    boolean save(Group group);

    Group findByTelegramID(long groupID);

}
