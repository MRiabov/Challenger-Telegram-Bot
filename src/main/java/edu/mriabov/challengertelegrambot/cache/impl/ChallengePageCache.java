package edu.mriabov.challengertelegrambot.cache.impl;

import edu.mriabov.challengertelegrambot.cache.PageCache;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChallengePageCache extends PageCache<Long, Challenge> {

    @Override
    public void put(Long chatID, Page<Challenge> page) {
        log.info("A page of CHALLENGES from chatID {} was put into the challengePageCache. {}", chatID, page.toString());
        super.put(chatID, page);
    }
}
