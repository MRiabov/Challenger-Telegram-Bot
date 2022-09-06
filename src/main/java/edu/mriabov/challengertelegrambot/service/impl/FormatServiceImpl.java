package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormatServiceImpl implements FormatService {
    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final UserService userService;

    @Override
    public String format(long chatID, String input) {
        Optional<User> userOptional = userService.getUserByTelegramId(chatID);
        if (userOptional.isEmpty()) return Buttons.USER_NOT_FOUND.getMessage();
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
        Page<Chat> page = chatPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty()) return "Hey, it seems, that your chat list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i+1).append("️⃣ ").append(page.getContent().get(i).getName());
        }
        return result.toString();
    }

    private String userPageToListConverter(long chatID) {
        Page<User> page = userPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty()) return "Hey, it seems, that your user list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i+1).append("️⃣ ")
                    .append(page.getContent().get(i).getFirstName())
                    .append(" ")
                    .append(page.getContent().get(i).getLastName()!=null?
                            page.getContent().get(i).getLastName():"")
                    .append("\n");
        }
        return result.toString();
    }
}
