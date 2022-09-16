package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.dao.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
                challengeInPrivateConfirmation(userID),//9
                challengeInGroupConfirmation(userID),//10
                myChallengesList(userID)//11
        );
    }

    private String chatPageToListConverter(long chatID) {
        Page<Group> page = chatPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty())
            return "Hey, it seems, that your chat list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i + 1).append("️⃣ ").append(page.getContent().get(i).getGroupName());
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

    private String challengeInPrivateConfirmation(long userID) {
        if (!challengeCache.contains(userID)) return null;
        Challenge challenge = challengeCache.get(userID);
        if (challenge.getDifficulty() == null || challenge.getArea() == null || challenge.getUsers() == null)
            return null;
        StringBuilder challengeInfo = new StringBuilder();
        challengeInfo
                .append("\uD83E\uDD3C\u200D♀️Group: ").append(challenge.getGroup().getGroupName())
                .append("\n\uD83C\uDFCB️\u200D♂️Users: ");
        if (challenge.getUsers().size() > 4) challengeInfo.append(challenge.getUsers().size()).append(" users");
        else for (User user : challenge.getUsers())
            challengeInfo.append(user.getFirstName()).append(" ")
                    .append(user.getLastName() != null ? user.getLastName() : "");
        challengeInfo
                .append("\n\uD83C\uDF96Difficulty: ").append(challenge.getDifficulty().text)
                .append("\n\uD83C\uDFF9Area: ").append(challenge.getArea().text)
                .append("\n\n\uD83D\uDCDDChallenge description: ").append(challenge.getDescription())
                .append("\n\n\uD83D\uDCB8It costs: ").append(billingFormatter(userID, billingService.challengePrice(challenge)));
        return challengeInfo.toString();
    }

    private String billingFormatter(long userID, int price) {
        if (billingService.isEnoughCoins(userID, price)) return "\uD83D\uDC8E" + price;
        else return "~\uD83D\uDC8E" + price + "~";
    }

    private String billingFormatter(Challenge challenge) {
        if (challenge.getCreatedBy() == null) return "Cannot calculate price! Creator is not registered!";
        if (challenge.getDifficulty() == null) return "Cannot calculate price! Difficulty is required!";
        int price = billingService.challengePrice(challenge);
        if (billingService.isEnoughCoins(challenge.getCreatedBy().getId(), price)) return "\uD83D\uDC8E" + price;
        else return "~\uD83D\uDC8E" + price + "~";
    }

    private String challengeInGroupConfirmation(long userID) {
        if (!challengeCache.contains(userID)) return null;
        Challenge challenge = challengeCache.get(userID);
        return "\n\uD83C\uDFF9Area: " +
                (challenge.getArea() != null ? challenge.getArea().text : "NO AREA FOUND!") +
                "\n\uD83C\uDF96Difficulty: " +
                (challenge.getDifficulty() != null ? challenge.getDifficulty().text : "NO DIFFICULTY FOUND!") +
                "\nRecurring time: " + (challenge.getRecurringTime() != null ? challenge.getRecurringTime() : "Challenge is not recurring") +
                "\n\n\uD83D\uDCDDChallenge description: " +
                challenge.getDescription() +
                "\n" + (challenge.isFree() ? "The challenge is free as it is created by an admin." :
                "\n\n\uD83D\uDCB8It costs: " + billingFormatter(challenge));
    }

    private String myChallengesList(long userID) {
        List<Challenge> challenges = userService.findChallengesByTelegramID(userID, Pageable.unpaged()).getContent();
        if (challenges.size() == 0) {
            return "All your challenges are completed! Time to advance in fields, other then listed here." +
                    "\nJust don't stop!";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < challenges.size(); i++) {
            stringBuilder.append(i).append(". ")
                    .append("\uD83C\uDFF9Area: ").append(challenges.get(i).getArea().text)
                    .append("\n\uD83C\uDF96Difficulty: ").append(challenges.get(i).getDifficulty().text)
                    .append("\n\uD83D\uDCDDChallenge description: ").append(challenges.get(i).getDescription())
                    .append("\nExpires at: ").append(challenges.get(i).getExpiresAt()).append(". ")
                    .append("")//todo how much time left
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
