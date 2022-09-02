package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChatRepository;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Page<User> findAllByTelegramID(long chatID,int page) {
        return chatRepository.findAllByTelegramID(chatID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                .withPage(page));
    }

    @Override
    public long selectOnPageByNumber(long chatID, int page, int number) {
        return findAllByTelegramID(chatID,page).getContent().get(number).getTelegramId();
    }
}
