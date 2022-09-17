package edu.mriabov.challengertelegrambot.dao.daoservice.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.GroupRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils.PAGE_SIZE;

@Service
@Slf4j
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
        return userRepository.findChatsByTelegramId(chatID, Pageable.ofSize(PAGE_SIZE).withPage(page));
    }

    @Override
    public Page<Group> findChatsByPageable(long chatID, Pageable pageable) {
        return userRepository.findChatsByTelegramId(chatID, pageable);
    }

    @Override
    public Page<Group> findMatchingChats(long chatID1, long chatID2) {
        return userRepository.findMatchingChatsFor2Users(chatID1, chatID2, Pageable.ofSize(PAGE_SIZE));
    }

    @Override
    public boolean addChat(long userID, Group group) {
        if (userRepository.findChatsByTelegramId(userID, Pageable.unpaged()).getContent().contains(group)) return false;
        if (!groupRepository.existsByTelegramId(group.getTelegramId())) return false;

        Optional<User> userOptional = userRepository.getUserByTelegramId(userID);
        if (userOptional.isEmpty()) return false;
        User user = userOptional.get();
        Set<Group> groups = userRepository.findChatsByTelegramId(userID);
        groups.add(group);
        user.setGroups(groups);
        save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void completeChallenge(long userID, Challenge challenge) {
        User user;
        Optional<User> userOptional = userRepository.getUserByTelegramId(userID);
        if (userOptional.isPresent()) user=userOptional.get();
        else {
            log.error("Unregistered user " + userID + " attempted to complete a challenge");
            return;
        }
        int incrementBy = challenge.getDifficulty().reward;//should it be like this? should tasks be rewarded so little?
        switch (challenge.getArea()) {
            case FINANCES -> user.getUserStats().setFinances(user.getUserStats().getFinances() + incrementBy);
            case MINDFULNESS -> user.getUserStats().setMindfulness(user.getUserStats().getMindfulness() + incrementBy);
            case FITNESS -> user.getUserStats().setFitness(user.getUserStats().getFitness() + incrementBy);
            case RELATIONSHIPS -> user.getUserStats().setRelationships(user.getUserStats().getRelationships() + incrementBy);
        }
        user.setCoins(user.getCoins()+incrementBy);//custom and global challenges differ in reward?...
        user.setChallenges(userRepository.getAllChallengesButOne(userID, challenge.getId()));
        userRepository.save(user);
    }

    @Override
    public Page<Challenge> findChallengesByTelegramID(long userID, Pageable pageable) {
        return userRepository.findAllChallengesByTelegramId(userID, pageable);
    }
}
