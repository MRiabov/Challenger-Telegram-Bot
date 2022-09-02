package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;

import java.util.concurrent.TimeUnit;

public class UserPageCache {

    private static final Cache<Long, Page<User>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public static Page<User> getCurrentPage(long chatID) {
        return cache.asMap().getOrDefault(chatID, null);
    }

    public static void put(long chatID, Page<User> page) {
        cache.put(chatID, page);
    }

}
