package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
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
    private final ChallengeCache challengeCache;
    private final UserService userService;
    private final BillingService billingService;

    @Override
    public String format(long userID, String input) {
        Optional<User> userOptional = userService.getUserByTelegramId(userID);
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
                chatPageToListConverter(userID),// 7
                userPageToListConverter(userID),//8
                challengeConfirmation(userID)
        );
    }

    private String chatPageToListConverter(long chatID) {
        Page<Group> page = chatPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty())
            return "Hey, it seems, that your chat list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i + 1).append("️⃣ ").append(page.getContent().get(i).getName());
        }
        return result.toString();
    }

    private String userPageToListConverter(long chatID) {
        Page<User> page = userPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty())
            return "Hey, it seems, that your user list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i + 1).append("️⃣ ")
                    .append(page.getContent().get(i).getFirstName())
                    .append(" ")
                    .append(page.getContent().get(i).getLastName() != null ?
                            page.getContent().get(i).getLastName() : "")
                    .append("\n");
        }
        return result.toString();
    }

    private String challengeConfirmation(long userID) {
        if (!challengeCache.contains(userID)) return null;
        Challenge challenge = challengeCache.get(userID);
        if (challenge.getDifficulty()==null||challenge.getArea()==null||challenge.getUsers()==null) return null;
        StringBuilder challengeInfo = new StringBuilder();
        challengeInfo
                .append("\uD83E\uDD3C\u200D♀️Group: ").append(challenge.getGroup().getName())
                .append("\n\uD83C\uDFCB️\u200D♂️Users: ");
        for (User user : challenge.getUsers()) challengeInfo.append(user.getFirstName()).append(" ")
                .append(user.getLastName() != null ? user.getLastName() : "");
        challengeInfo
                .append("\n\uD83C\uDF96Difficulty: ").append(challenge.getDifficulty())
                .append("\n\uD83C\uDFF9Area: ").append(challenge.getArea())
                .append("\n\n\uD83D\uDCDDChallenge description: ").append(challenge.getDescription())
                .append("\n\n\uD83D\uDCB8It costs: ").append(billingFormatter(userID, billingService.challengePrice(challenge)));
        return challengeInfo.toString();
    }

    private String billingFormatter(long userID, int price) {
        if (billingService.isEnoughCoins(userID, price)) return "\uD83D\uDC8E" + price;
        else return "~\uD83D\uDC8E" + price + "~";
    }
}
