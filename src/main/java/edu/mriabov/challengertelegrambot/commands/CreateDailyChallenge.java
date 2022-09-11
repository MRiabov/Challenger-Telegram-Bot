package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.Time;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class CreateDailyChallenge extends BotCommand{

    private final UserService userService;
    private final GroupService groupService;
    private Message message;


    public CreateDailyChallenge(UserService userService, GroupService groupService) {
        super("daily", "create a recurring challenge. Type /help to know how.");
        this.userService = userService;
        this.groupService = groupService;
    }

    private Optional<Time> getChallengeTime(String message){
        for (String s : message.split(" ")) {
            if (s.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) return Optional.of(Time.valueOf(s));
        }
        return Optional.empty();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userService.getUserByTelegramId(user.getId()).get());
        challenge.setUsers(groupService.findAllUsers(chat.getId()));
        challenge.setDescription(message.getText().substring(TelegramUtils.getOffset(message.getText())));
        challenge.setCreatedAt(Instant.now());
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        this.message = message;
        super.processMessage(absSender, message, arguments);
    }
}
