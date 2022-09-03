package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class ChatPageCache implements PageCache<Long, Chat> {

    private static final Cache<Long, Page<Chat>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    @Override
    public void put(Long chatID, Page<Chat> page) {
        cache.put(chatID, page);
    }

    @Override
    public Page<Chat> getCurrentPage(Long chatID) {
        return cache.asMap().getOrDefault(chatID, Page.empty());
    }

    @Override
    public Pageable getNextOrLastPageable(Long chatID) {
        return cache.asMap().get(chatID).nextOrLastPageable();
    }

    @Override
    public Pageable getPreviousOrLastPageable(Long chatID) {
        return cache.asMap().get(chatID).previousOrFirstPageable();
    }

    @Override
    public boolean contains(Long chatID) {
        return cache.asMap().containsKey(chatID);
    }

    @Override
    public int getPageAmount(Long chatID) {
        return cache.asMap().get(chatID).getNumberOfElements();
    }

    @Override
    public Chat getOnCurrentPage(Long chatID, int position) {
        return getCurrentPage(chatID).getContent().get(position);
    }

    @Override
    public void cleanCache(Long chatID) {
        cache.invalidate(chatID);
    }
}
