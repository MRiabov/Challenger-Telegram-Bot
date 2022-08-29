package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.FormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormatServiceImpl implements FormatService {

    private final UserRepository userRepository;

    @Override
    public String format(long chatID,String input) {
        User user=userRepository.getReferenceById(chatID);
        UserStats userStats = user.getUserStats();
        return String.format(input,
                user.getFirstName(),
                userStats.getFinances(),
                userStats.getRelationships(),
                userStats.getFitness(),
                userStats.getFitness(),
                user.getCoins()


                );
    }
}
