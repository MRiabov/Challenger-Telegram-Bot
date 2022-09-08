package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.service.GroupService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.UserService;
import edu.mriabov.challengertelegrambot.service.UserStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final GroupService chatService;
    private final UserStatsService userStatsService;

    @Override
    public void registerUser(Message message) {
        User user = new User();//our project User
        org.telegram.telegrambots.meta.api.objects.User telegramUser = message.getFrom();//tg user
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setTelegramId(telegramUser.getId());
        user.setUsername(telegramUser.getUserName());
        user.setUserStats(userStatsService.create());
        userService.save(user);
    }

    @Override
    public void registerChat(Update update) {
        Group group = new Group();
        org.telegram.telegrambots.meta.api.objects.Chat telegramChat = update.getMyChatMember().getChat();
        group.setTelegramId(telegramChat.getId());
        group.setName(telegramChat.getTitle());
        chatService.save(group);
    }
}
