package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.FormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormatServiceImpl implements FormatService {

    private final UserRepository userRepository;
    @Override
    public String format(long chatID,String input) {
        Optional<User> userOptional=userRepository.getUserByTelegramId(chatID);
        if (userOptional.isPresent()) {
            User user=userOptional.get();
            UserStats userStats = user.getUserStats();
            return String.format(input,
                    user.getFirstName(),//1s
                    userStats.getFinances(),//2
                    userStats.getRelationships(),//3
                    userStats.getFitness(),//4
                    userStats.getMindfulness(),//5
                    user.getCoins()//6
                    // 7
            );
        } else return "Error: User doesn't exist! Please register through /start!";
    }
}
