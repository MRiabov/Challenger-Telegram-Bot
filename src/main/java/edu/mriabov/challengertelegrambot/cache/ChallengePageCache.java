package edu.mriabov.challengertelegrambot.cache;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChallengePageCache extends PageCache<Long, Challenge> {

    @Override
    public void put(Long chatID, Page<Challenge> page) {
        log.info("A page of CHALLENGES from chatID " + chatID + " was put into the challengePageCache. " + page.toString());
        cache.put(chatID, page);
    }
}
