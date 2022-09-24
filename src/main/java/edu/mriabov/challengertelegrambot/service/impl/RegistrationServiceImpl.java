package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserStatsService;
import edu.mriabov.challengertelegrambot.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final GroupService groupService;
    private final UserStatsService userStatsService;
    private final SenderService senderService;

    @Override
    public void registerUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user = new User();//our project User
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
        group.setGroupName(telegramChat.getTitle());
        groupService.save(group);
    }

    @Override
    public void linkUserToGroup(long userID, long groupID) {
        Group group = groupService.findByTelegramID(groupID);
        boolean chatSuccessfullyLinked = userService.addChat(userID, group);
        if (chatSuccessfullyLinked) {
            log.info("User " + userID + " has successfully linked a chat " + group.getGroupName());
            senderService.sendMessages(userID, Replies.CHAT_SUCCESSFULLY_LINKED.text.formatted(group.getGroupName()));
        } else
            log.error("User " + userID + " has failed to add a chat via /start. It was an automatic request!!!");
    }
}
