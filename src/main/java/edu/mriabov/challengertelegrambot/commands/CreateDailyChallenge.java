package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.UserService;
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
    private Optional<Time> getChallengeTime(String message) {
        for (String s : message.split(" ")) {
            if (s.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) return Optional.of(Time.valueOf(s));
        }
        return Optional.empty();
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
        Challenge challenge = TelegramUtils.challengeBasicInfo(arguments);
        challenge.setCreatedBy(userService.getUserByTelegramId(message.getFrom().getId()).get());
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(TelegramUtils.getOffset(message.getText())));
        challenge.setCreatedAt(Instant.now());
    }
}
