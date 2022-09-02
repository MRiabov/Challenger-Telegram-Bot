package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;

import java.util.concurrent.TimeUnit;

public class ChallengeCache {

    private final Cache<Long, Challenge> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public void put(Long chatID, Challenge challenge) {
        cache.put(chatID, challenge);
    }

    public Challenge get(Long chatID) {
        return cache.asMap().get(chatID);
    }

    public boolean contains(Long key) {
        return cache.asMap().containsKey(key);
    }

}
