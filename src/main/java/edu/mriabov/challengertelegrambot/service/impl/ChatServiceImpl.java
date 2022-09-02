package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.repository.ChatRepository;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.utils.cache.PageNumCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final PageNumCache pageNumCache;
    private final ChatRepository chatRepository;

    @Override
    public Page<User> findAllByTelegramID(long chatID,int page) {
        return chatRepository.findAllByTelegramID(chatID, Pageable.ofSize(ButtonsMappingUtils.PAGE_SIZE)
                .withPage(page));
    }

    @Override
    public long selectOnPageByNumber(long chatID, int number) {
        int page = pageNumCache.get(chatID);
        return findAllByTelegramID(chatID,page).getContent().get(number).getTelegramId();
    }
}
