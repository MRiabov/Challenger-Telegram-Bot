package edu.mriabov.challengertelegrambot.cache.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class ChallengeCache {

    private final Cache<Long, Challenge> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public void put(Long chatID, Challenge challenge) {
        cache.put(chatID, challenge);
    }

    public Challenge get(Long userID) {
        return cache.asMap().get(userID);
    }

    public boolean contains(Long key) {
        return cache.asMap().containsKey(key);
    }

}
