package edu.mriabov.challengertelegrambot.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class PageNumCache implements edu.mriabov.challengertelegrambot.utils.cache.Cache<Long,Integer> {

    private static final Cache<Long, Integer> pageNumCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    @Override
    public void put(Long key, Integer value) {
        pageNumCache.put(key, value);
    }

    @Override
    public Integer get(Long chatID) {
        return pageNumCache.asMap().get(chatID);
    }

    @Override
    public boolean contains(Long chatID) {
        return pageNumCache.asMap().containsKey(chatID);
    }

    public void resetToDefault(long chatID){
        put(chatID,1);
    }
}
