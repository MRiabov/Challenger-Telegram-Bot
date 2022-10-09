package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.cache.impl.ChallengeCache;
import edu.mriabov.challengertelegrambot.cache.impl.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.impl.UserPageCache;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.service.BillingService;
import edu.mriabov.challengertelegrambot.service.FormatService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormatServiceImpl implements FormatService {
    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final ChallengeCache challengeCache;
    private final UserService userService;
    private final BillingService billingService;

    @Override
    public String format(long userID, String input) {
        Optional<User> userOptional = userService.getUserByTelegramId(userID);
        if (userOptional.isEmpty()) {
            return input;
        }
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
                myChallengesList(userID),//11
                challengeSkipConfirmation(userID),//12
                listChallengeDescriptions(userID),//13
                listGoals(userID)//14
        );
    }

    private String chatPageToListConverter(long chatID) {
        Page<Group> page = chatPageCache.getCurrentPage(chatID);
        StringBuilder result = new StringBuilder();
        if (page.isEmpty())
            return "Hey, it seems, that your chat list is empty. How about finding some community to join?";
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            result.append(i + 1).append("️⃣ ").append(page.getContent().get(i).getGroupName()).append("\n");
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
        if (challenge.getId() != 0 || challenge.getArea() == null || challenge.getUsers() == null ||
                challenge.getDifficulty() == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\uD83E\uDD3C\u200D♀️<b>Group:</b> ").append(challenge.getGroup() != null ? challenge.getGroup().getGroupName() : "FOR YOURSELF")
                .append("\n\uD83D\uDC68\u200D\uD83D\uDCBB<b>Users: </b>");
        listUsers(challenge, stringBuilder);
        stringBuilder
                .append("\n\uD83C\uDF96<b>Difficulty: </b>").append(challenge.getDifficulty().text)
                .append("\n\uD83C\uDFF9<b>Area: </b>").append(challenge.getArea().text)
                .append("\n\n\uD83D\uDCDD<b>Challenge description: </b>").append(challenge.getDescription())
                .append("\n\n\uD83D\uDCB8<b>It costs: </b>")
                .append(!challenge.isFree() ? billingFormatter(userID, billingService.challengePrice(challenge)) : "FREE");
        return stringBuilder.toString();
    }

    private static void listUsers(Challenge challenge, StringBuilder stringBuilder) {
        if (challenge.getUsers().isEmpty()) {
            stringBuilder.append("ERROR! 0 users selected.");
            return;
        }
        if (challenge.getUsers().size() > 4) stringBuilder.append(challenge.getUsers().size()).append(" users");
        else for (User user : challenge.getUsers()) {
            stringBuilder.append("<a href=\"tg://user?id=")
                    .append(user.getTelegramId())
                    .append("\">")
                    .append(user.getFirstName())
                    .append(user.getLastName() != null ? " " + user.getLastName() : "")
                    .append("</a>, ");
        }
    }

    private String billingFormatter(long userID, int price) {
        if (billingService.isEnoughCoins(userID, price)) return "\uD83D\uDC8E" + price;
        else return "<s>\uD83D\uDC8E" + price + "</s>";
    }

    private String billingFormatter(Challenge challenge) {
        if (challenge.getCreatedBy() == null) return "Cannot calculate price! Creator is not registered!";
        if (challenge.getDifficulty() == null) return "Cannot calculate price! Difficulty is required!";
        int price = billingService.challengePrice(challenge);
        if (billingService.isEnoughCoins(challenge.getCreatedBy().getId(), price)) return "\uD83D\uDC8E" + price;
        else return "<s>\uD83D\uDC8E " + price + " </s>";
    }

    private String challengeInGroupConfirmation(long userID) {
        if (!challengeCache.contains(userID) || challengeCache.get(userID).getId() != 0) return null;
        Challenge challenge = challengeCache.get(userID);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n\uD83C\uDFF9<b>Area: </b>")
                .append(challenge.getArea() != null ? challenge.getArea().text : "NO AREA FOUND!")
                .append("\n\uD83C\uDF96<b>Difficulty: </b>")
                .append(challenge.getDifficulty() != null ? challenge.getDifficulty().text : "NO DIFFICULTY FOUND!")
                .append("\n\uD83C\uDFCB️\u200D♂️<b>Users: </b>");
        listUsers(challenge, stringBuilder);
        stringBuilder
                .append("\n⏰<b>Recurring time: </b>")
                .append(challenge.getRecurringTime() != null ? challenge.getRecurringTime() : "Challenge is not recurring")
                .append("\n\n\uD83D\uDCDD<b>Challenge description: </b>")
                .append(challenge.getDescription())
                .append("\n\n\uD83D\uDCB8<b>It costs: </b>")
                .append(challenge.isFree() ? "The challenge is free as it is created by an admin." :
                        billingFormatter(challenge))
                .append("\n")
                .append(challenge.getDifficulty() != null && challenge.getDescription() != null && challenge.getArea() != null ?
                        "Press /confirm to bill coins and confirm the challenge" : "Challenge is incorrect!");
        return stringBuilder.toString();
    }

    private String myChallengesList(long userID) {
        List<Challenge> challenges = userService.findChallengesByTelegramID(userID, Pageable.unpaged()).getContent();
        if (challenges.isEmpty()) {
            return """
                                        
                    <b>All your challenges are completed!</b> Time to advance in fields, other then listed here.
                    Just don't stop!
                    """;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < challenges.size(); i++) {
            stringBuilder
                    .append("\n\n")
                    .append(i + 1).append(". ")
                    .append("\uD83C\uDFF9<b>Area: </b>").append(challenges.get(i).getArea().text)
                    .append("\n\uD83C\uDF96<b>Difficulty: </b>").append(challenges.get(i).getDifficulty().text)
                    .append("\n\uD83D\uDCDD<b>Challenge description: </b>").append(challenges.get(i).getDescription())
                    .append("\n<b>Expires at: </b>").append(TelegramUtils.formatter.format(challenges.get(i).getExpiresAt()))
                    .append("\n<b>").append(countTimeLeft(challenges.get(i).getExpiresAt())).append("</b>");
        }
        return stringBuilder.toString();
    }

    private String listChallengeDescriptions(long userID) {
        Page<Challenge> challenges = userService.findChallengesByTelegramID(userID, Pageable.ofSize(9));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < challenges.getNumberOfElements(); i++)
            stringBuilder
                    .append(i + 1).append("️⃣").append(challenges.getContent().get(i).getDescription())
                    .append("\n");
        return stringBuilder.toString();
    }

    private String challengeSkipConfirmation(long userID) {
        if (challengeCache.contains(userID) && challengeCache.get(userID).getId() != 0)
            return billingFormatter(userID, challengeCache.get(userID).getDifficulty().price);
        return null;
    }

    private String countTimeLeft(LocalDateTime expiresAt) {//counts how much time left, if hour[S] is needed
        long untilHours = LocalDateTime.now().until(expiresAt, ChronoUnit.HOURS);
        if (untilHours <= 24) {
            return untilHours + " hour" + (untilHours > 1 ? "s" : "") + " left";
        } else {
            long until = LocalDateTime.now().until(expiresAt, ChronoUnit.DAYS);
            return until + " day" + (until > 1 ? "s" : "") + " left";
        }
    }

    private String listGoals(long userID) {
        Set<Challenge> goals = userService.findAllGoals(userID);
        StringBuilder stringBuilder = new StringBuilder();
        if (!goals.isEmpty()) {
            stringBuilder.append("<b>Your goals:</b>\n");
            for (Challenge goal : goals) {
                stringBuilder
                        .append('\n').append("\uD83C\uDFAF").append(goal.getDescription())
                        .append('\n').append(countTimeLeft(goal.getExpiresAt()))
                        .append('\n');
            }
        } else stringBuilder.append("<b>Time to set some goals!</b> Don't wait until someone else does it for you.");
        return stringBuilder.toString();
    }
}
