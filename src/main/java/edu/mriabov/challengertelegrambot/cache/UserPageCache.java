package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.TimeUnit;

public class UserPageCache implements PageCache<Long, User> {

    private final Cache<Long, Page<User>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    @Override
    public void put(Long chatID, Page<User> value) {
        cache.put(chatID, value);
    }

    @Override
    public Page<User> getCurrentPage(Long chatID) {
        return cache.asMap().getOrDefault(chatID, null);
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
    public User getOnCurrentPage(Long chatID, int position) {
        return getCurrentPage(chatID).getContent().get(position);
    }

    @Override
    public void cleanCache(Long chatID) {
        cache.invalidate(chatID);
    }
}
