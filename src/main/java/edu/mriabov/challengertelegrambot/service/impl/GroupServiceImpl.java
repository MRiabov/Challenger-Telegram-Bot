package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Page<User> findUsersByTelegramID(long userID, long groupID, int page) {
        return groupRepository.findUsersByTelegramID(userID, groupID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                .withPage(page));
    }

    @Override
    public Page<User> findUsersByPageable(long userID, long groupID, Pageable pageable) {
        return groupRepository.findUsersByTelegramID(userID, groupID,pageable);
    }

    @Override
    public Set<User> findAllUsers(long groupID) {
        return groupRepository.findAllUsersByTelegramID(groupID);
    }

    @Override
    public boolean save(Group group) {
        if (groupRepository.existsByTelegramId(group.getTelegramId())) return false;
        groupRepository.save(group);
        return true;
    }

    @Override
    public Group findByTelegramID(long groupID) {
        return groupRepository.findByTelegramId(groupID);
    }
}
