package edu.mriabov.challengertelegrambot.cache;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatPageCache extends PageCache<Long, Group> {

    @Override
    public void put(Long chatID, Page<Group> page) {
        log.info("A page of CHATS from chatID " + chatID + " was put into the chatPageCache. " + page.toString());
        super.put(chatID, page);
    }
}
