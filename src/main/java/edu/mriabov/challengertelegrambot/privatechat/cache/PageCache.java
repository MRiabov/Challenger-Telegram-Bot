package edu.mriabov.challengertelegrambot.privatechat.cache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface PageCache<K, V> {

    void put(K chatID, Page<V> value);

    Page<V> getCurrentPage(K chatID);

    Pageable getNextOrLastPageable(K chatID);

    Pageable getPreviousOrLastPageable(K chatID);

    boolean contains(K chatID);

    int getPageAmount(K chatID);

    V getOnCurrentPage(K chatID,int position);

    void cleanCache(K chatID);
}
