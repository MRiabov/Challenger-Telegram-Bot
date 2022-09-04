package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ChatService {

    Page<User> findAllByTelegramID(long chatID,int page);
    Page<User> findAllByPageable(long chatID, Pageable pageable);



}
