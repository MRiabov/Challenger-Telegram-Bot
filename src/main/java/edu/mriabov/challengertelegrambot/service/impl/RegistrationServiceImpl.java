package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.model.Chat;
import edu.mriabov.challengertelegrambot.dao.model.User;
import edu.mriabov.challengertelegrambot.dao.model.UserStats;
import edu.mriabov.challengertelegrambot.service.ChatService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ChatService chatService;

    @Override
    public void registerUser(Message message) {
        User user = new User();//our project User
        org.telegram.telegrambots.meta.api.objects.User telegramUser = message.getFrom();//tg user
        user.setCoins(0);
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setTelegramId(telegramUser.getId());
        user.setUsername(telegramUser.getUserName());
        user.setUserStats(new UserStats());
        userService.save(user);
    }

    @Override
    public void registerChat(Update update) {
        Chat chat = new Chat();
        org.telegram.telegrambots.meta.api.objects.Chat telegramChat = update.getMyChatMember().getChat();
        chat.setTelegramID(telegramChat.getId());
        chat.setName(telegramChat.getTitle());
        chat.setAddedAt(LocalDateTime.now());
        chatService.save(chat);
    }
}
