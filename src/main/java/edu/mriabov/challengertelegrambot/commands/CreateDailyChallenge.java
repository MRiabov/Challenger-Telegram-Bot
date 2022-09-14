package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengeCache;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.dao.service.GroupService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import edu.mriabov.challengertelegrambot.dao.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.Time;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateDailyChallenge implements IBotCommand {

    private final UserService userService;
    private final GroupService groupService;
    private final SenderService senderService;
    private final ChallengeCache challengeCache;

    private Time getChallengeTime(String[] arguments) {
        for (String word : arguments) {
            if (word.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) return Time.valueOf(word);
        }
        return null;
    }

    @Override
    public String getCommandIdentifier() {
        return "daily";
    }

    @Override
    public String getDescription() {
        return "create a recurring challenge. Type /help to know how.";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (message.getChat().isUserChat()) {
            senderService.replyToMessage(message, Replies.WRONG_CHAT_TYPE.text);
            return;
        }
        Optional<User> userByTelegramId = userService.getUserByTelegramId(message.getFrom().getId());
        if (userByTelegramId.isEmpty()){
            senderService.replyToMessage(message,Replies.USER_NOT_REGISTERED.text);
            return;
        }
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userByTelegramId.get());
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(TelegramUtils.getOffset(message.getText())));
        challenge.setRecurringTime(getChallengeTime(arguments));
        challenge.setGroup(groupService.findByTelegramID(message.getChatId()));
        challenge.setCreatedAt(Instant.now());
        if (challenge.getDifficulty() == null || challenge.getUsers().size() == 0 || challenge.getArea() == null)
            senderService.replyToMessage(message, Replies.INVALID_CUSTOM_CHALLENGE.text);
        else {
            senderService.replyToMessage(message, "SUCCESS. The operation will take ... coins.");
            challengeCache.put(message.getFrom().getId(), challenge);
        }
    }
}
