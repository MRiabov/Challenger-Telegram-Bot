package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ChatService {

    Page<User> findUsersByTelegramID(long chatID, int page);

    Page<User> findUsersByPageable(long chatID, Pageable pageable);

    boolean save(Chat chat);

    Chat findByTelegramID(long chatID);

}
