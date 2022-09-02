package edu.mriabov.challengertelegrambot.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;

import java.util.concurrent.TimeUnit;

public class ChallengeCache implements edu.mriabov.challengertelegrambot.utils.cache.Cache<Long,Challenge> {

    private final Cache<Long, Challenge> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    @Override
    public void put(Long chatID, Challenge challenge) {
        cache.put(chatID, challenge);
    }

    @Override
    public Challenge get(Long chatID) {
        return cache.asMap().get(chatID);
    }

    @Override
    public boolean contains(Long key) {
        return cache.asMap().containsKey(key);
    }


}
