package edu.mriabov.challengertelegrambot.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.TimeUnit;


@Slf4j
public abstract class PageCache<K, V> {

    protected final Cache<K, Page<V>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public void put(K chatID, Page<V> page) {
        cache.put(chatID, page);
    }

    public Page<V> getCurrentPage(K chatID) {
        return cache.asMap().getOrDefault(chatID, Page.empty());
    }

    public Pageable getNextOrLastPageable(K chatID) {
        Page<V> page = cache.asMap().get(chatID);
        log.info("User " + chatID + " attempted to flip a page forward. Received " + page.toString() + "from cache.");
        return cache.asMap().get(chatID).nextOrLastPageable();
    }

    public Pageable getPreviousOrLastPageable(K chatID) {
        Page<V> page = cache.asMap().get(chatID);
        log.info("User " + chatID + " attempted to flip a page back. Received " + page.toString() + "from cache.");
        return cache.asMap().get(chatID).previousOrFirstPageable();
    }

    public boolean contains(K chatID) {
        return cache.asMap().containsKey(chatID);
    }

    public int getPageAmount(K chatID) {
        return cache.asMap().get(chatID).getNumberOfElements();
    }

    public V getOnCurrentPage(K chatID, int position) {
        return cache.asMap().get(chatID).getContent().get(position);
    }

}
