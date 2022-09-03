package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormatServiceImpl implements FormatService {
    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final UserRepository userRepository;

    @Override
    public String format(long chatID, String input) {
        Optional<User> userOptional = userRepository.getUserByTelegramId(chatID);
        if (userOptional.isEmpty()) return "Error: User doesn't exist! Please register through /start!";
        User user = userOptional.get();
        UserStats userStats = user.getUserStats();
        return String.format(input,
                user.getFirstName(),//1s
                userStats.getFinances(),//2
                userStats.getRelationships(),//3
                userStats.getFitness(),//4
                userStats.getMindfulness(),//5
                user.getCoins(),//6
                chatPageToListConverter(chatID),// 7
                userPageToListConverter(chatID)//8
        );
    }

    private String chatPageToListConverter(long chatID) {
        Page<Chat> page = ChatPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= page.getNumberOfElements(); i++) {
            result.append(i).append("️⃣ ").append(page.getContent().get(i).getName());
        }
        return result.toString();
    }

    private String userPageToListConverter(long chatID) {
        Page<User> page = userPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= page.getNumberOfElements(); i++) {
            result.append(i).append("️⃣ ")
                    .append(page.getContent().get(i).getFirstName())
                    .append(page.getContent().get(i).getLastName());
        }
        return result.toString();
    }
}
