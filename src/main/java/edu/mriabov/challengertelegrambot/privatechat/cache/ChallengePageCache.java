package edu.mriabov.challengertelegrambot.privatechat.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ChallengePageCache implements PageCache<Long, Challenge> {

    private final Cache<Long, Page<Challenge>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    @Override
    public void put(Long chatID, Page<Challenge> page) {
        log.info("A page of CHATS from chatID " + chatID + " was put into the chatPageCache. " + page.toString());
        cache.put(chatID, page);
    }

    @Override
    public Page<Challenge> getCurrentPage(Long chatID) {
        return cache.asMap().getOrDefault(chatID, Page.empty());
    }

    @Override
    public Pageable getNextOrLastPageable(Long chatID) {
        Page<Challenge> page = cache.asMap().get(chatID);
        log.info("User " + chatID + " attempted to flip a page forward. Received " + page.toString() + "from cache.");
        return cache.asMap().get(chatID).nextOrLastPageable();
    }

    @Override
    public Pageable getPreviousOrLastPageable(Long chatID) {
        Page<Challenge> page = cache.asMap().get(chatID);
        log.info("User " + chatID + " attempted to flip a page back. Received " + page.toString() + "from cache.");
        return cache.asMap().get(chatID).previousOrFirstPageable();
    }

    @Override
    public boolean contains(Long chatID) {
        return cache.asMap().containsKey(chatID);
    }

    @Override
    public int getPageAmount(Long chatID) {
        return cache.asMap().get(chatID).getNumberOfElements();
    }

    @Override
    public Challenge getOnCurrentPage(Long chatID, int position) {
        return cache.asMap().get(chatID).getContent().get(position);
    }

    @Override
    public void cleanCache(Long chatID) {
        cache.invalidate(chatID);
    }
}
