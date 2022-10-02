package edu.mriabov.challengertelegrambot.cache;

import edu.mriabov.challengertelegrambot.dao.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserPageCache extends PageCache<Long, User> {
    @Override
    public void put(Long chatID, Page<User> page) {
        log.info("A page of USERS from chatID " + chatID + " was put into the challengePageCache. " + page.toString());
        super.put(chatID,page);
    }
}
