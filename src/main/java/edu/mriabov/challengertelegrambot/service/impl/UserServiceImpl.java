package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils.PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public boolean existsByTelegramId(long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    @Override
    public Optional<User> getUserByTelegramId(long telegramId) {
        return userRepository.getUserByTelegramId(telegramId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username.startsWith("@")) username = username.substring(1);
        return userRepository.getUserByUsername(username);
    }

    @Override
    public Page<Group> findChatsByTelegramId(long chatID, int page) {
        return userRepository.findGroupsByTelegramId(chatID,
                Pageable.ofSize(PAGE_SIZE).withPage(page));
    }

    @Override
    public Page<Group> findChatsByPageable(long chatID, Pageable pageable) {
        return userRepository.findGroupsByTelegramId(chatID, pageable);
    }

    @Override
    public Page<Group> findMatchingChats(long chatID1, long chatID2) {
        return userRepository.findMatchingGroupsFor2Users(chatID1, chatID2, Pageable.ofSize(PAGE_SIZE));
    }

    @Override
    public boolean addChat(long userID, Group group) {
        if (userRepository.findGroupsByTelegramId(userID, Pageable.unpaged()).getContent().contains(group)) return false;
        if (!groupRepository.existsByTelegramId(group.getTelegramId())) return false;

        Optional<User> userOptional = userRepository.getUserByTelegramId(userID);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        Set<Group> groups = userRepository.findGroupsByTelegramId(userID);
        groups.add(group);
        user.setGroups(groups);
        save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
