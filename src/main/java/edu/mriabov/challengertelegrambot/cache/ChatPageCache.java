package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.TimeUnit;

public class ChatPageCache {

    private static final Cache<Long, Page<Chat>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public static void put(long chatID, Page<Chat> page) {
        cache.put(chatID, page);
    }

    public static Pageable getNextPageable(long chatID) {
        return cache.asMap().get(chatID).nextOrLastPageable();
    }

    public static Page<Chat> getCurrentPage(long chatID) {
        return cache.asMap().getOrDefault(chatID, null);
    }

    public static boolean contains(long chatID) {
        return cache.asMap().containsKey(chatID);
    }

    public static boolean contains(long chatID, int selectedNumber) {
        if (!contains(chatID)) return false;
        return cache.asMap().get(chatID).getContent().size() >= selectedNumber;
    }

    public static Chat getByNumber(long chatID, int selectedNumber) {
        return getCurrentPage(chatID).getContent().get(selectedNumber);
    }
}
