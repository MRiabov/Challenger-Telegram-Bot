package edu.mriabov.challengertelegrambot.cache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageCache<K, V> {

    void putFirst(K chatID, Page<V> value);

    Page<V> nextPage(K chatID);

    Page<V> getCurrentPage(K chatID);

    Pageable getNextOrLastPageable(K chatID);

    boolean contains(K chatID, Page<V> value);
}
