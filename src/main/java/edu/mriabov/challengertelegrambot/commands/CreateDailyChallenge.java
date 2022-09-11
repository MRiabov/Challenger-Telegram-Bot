package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;
import edu.mriabov.challengertelegrambot.privatechat.utils.TelegramUtils;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Time;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateDailyChallenge implements Command{

    private final UserService userService;
    private final GroupService groupService;

    @Override
    public String alias() {
        return "/daily";
    }

    @Override
    public String scope() {
        return null;
    }

    @Override
    public void execute(Message message) {
        Challenge challenge = TelegramUtils.challengeBasicInfo(message.getText());
        //difficulty, area, message
        challenge.setCreatedBy(userService.getUserByTelegramId(message.getFrom().getId()).get());
        challenge.setUsers(groupService.findAllUsers(message.getChatId()));
        challenge.setDescription(message.getText().substring(TelegramUtils.getOffset(message.getText())));
        challenge.setCreatedAt(Instant.now());
        //todo recurring event

    }

    private Optional<Time> getChallengeTime(String message){
        for (String s : message.split(" ")) {
            if (s.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) return Optional.of(Time.valueOf(s));
        }
        return Optional.empty();
    }
}
