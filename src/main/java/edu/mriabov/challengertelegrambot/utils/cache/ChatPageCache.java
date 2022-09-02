package edu.mriabov.challengertelegrambot.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import org.springframework.data.domain.Page;

import java.util.concurrent.TimeUnit;

public class ChatPageCache {

    private static final Cache<Long, Page<Chat>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public static void put(long chatID, Page<Chat> page) {
        cache.put(chatID, page);
    }

    public static Page<Chat> getCurrentPage(long chatID) {
        return cache.asMap().getOrDefault(chatID, null);
    }


}
