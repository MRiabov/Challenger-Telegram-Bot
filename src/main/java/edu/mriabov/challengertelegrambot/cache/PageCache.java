package edu.mriabov.challengertelegrambot.cache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageCache<K, V> {

    void put(K chatID, Page<V> value);

    Page<V> getCurrentPage(K chatID);

    Pageable getNextOrLastPageable(K chatID);

    Pageable getPreviousOrLastPageable(K chatID);

    boolean contains(K chatID);

    int getPageAmount(K chatID);

    V getOnCurrentPage(K chatID,int position);
}
