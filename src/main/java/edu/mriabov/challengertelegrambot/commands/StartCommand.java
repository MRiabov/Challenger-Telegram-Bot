package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.dao.service.GroupService;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import edu.mriabov.challengertelegrambot.dao.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Slf4j
public class StartCommand extends BotCommand {

    private final UserService userService;
    private final GroupService groupService;
    private final RegistrationService registrationService;

    public StartCommand(UserService userService, GroupService groupService, RegistrationService registrationService) {
        super("start", "(re)Start the bot!");
        this.userService = userService;
        this.groupService = groupService;
        this.registrationService = registrationService;
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (chat.getType().equals("private")) {
            absSender.execute(SendMessage.builder()
                    .chatId(chat.getId())
                    .text(Replies.WRONG_CHAT_TYPE.text)
                    .build());
            return;
        }
        if (!userService.existsByTelegramId(chat.getId())) registrationService.registerUser(user);
        absSender.execute(ButtonsMappingUtils.buildMessageWithKeyboard(chat.getId(), Buttons.ON_START_NEW_USER));
        //if this is a deep linking request, add the chat from the deep linking request.
        if (arguments.length > 0) addChat(arguments[0], chat, absSender);
    }

    @SneakyThrows
    private void addChat(String payload, Chat chat, AbsSender absSender) {
        boolean chatSuccessfullyLinked = userService.addChat(chat.getId(),
                groupService.findByTelegramID(Long.parseLong(payload.substring(7))));
        if (chatSuccessfullyLinked) {
            log.info("User " + chat.getId() + " has successfully linked a chat " + chat.getTitle());
            absSender.execute(SendMessage.builder()
                    .text(Replies.CHAT_SUCCESSFULLY_LINKED.text.formatted(chat.getTitle()))
                    .chatId(chat.getId())
                    .build());
        } else
            log.warn("User " + chat.getId() + " has failed to add a chat via /start. his payload: " + payload.substring(7));
    }


}
