package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;


public interface ChatService {

    Page<User> findAllByTelegramID(long chatID,int page);
    long selectOnPageByNumber(long chatID,int number);

}
