package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChatRepository;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Page<User> findUsersByTelegramID(long userID, long groupID, int page) {
        return chatRepository.findUsersByTelegramID(userID, groupID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                .withPage(page));
    }

    @Override
    public Page<User> findUsersByPageable(long userID, long groupID, Pageable pageable) {
        return chatRepository.findUsersByTelegramID(userID, groupID,pageable);
    }

    @Override
    public boolean save(Group group) {
        if (chatRepository.existsByTelegramID(group.getTelegramID())) return false;
        chatRepository.save(group);
        return true;
    }

    @Override
    public Group findByTelegramID(long groupID) {
        return chatRepository.findByTelegramID(groupID);
    }
}
