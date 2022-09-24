package edu.mriabov.challengertelegrambot.commands;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.daoservice.GroupService;
import edu.mriabov.challengertelegrambot.dao.daoservice.UserService;
import edu.mriabov.challengertelegrambot.groupchat.Replies;
import edu.mriabov.challengertelegrambot.privatechat.Buttons;
import edu.mriabov.challengertelegrambot.service.ValidatorService;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements IBotCommand {

    private final UserService userService;
    private final GroupService groupService;
    private final RegistrationService registrationService;
    private final ValidatorService validatorService;

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (validatorService.isGroupChat(message)) return;
        if (!userService.existsByTelegramId(message.getFrom().getId()))
            registrationService.registerUser(message.getFrom());
        absSender.execute(ButtonsMappingUtils.buildMessageWithKeyboard(message.getChatId(), Buttons.ON_START_NEW_USER));
        //if this is a deep linking request, add the chat from the deep linking request.
        if (arguments.length > 0) addChat(arguments[0], message.getChat(), absSender);
    }

    @SneakyThrows
    private void addChat(String payload, Chat chat, AbsSender absSender) {
        Group group = groupService.findByTelegramID(Long.parseLong(payload));
        boolean chatSuccessfullyLinked = userService.addChat(chat.getId(), group);
        if (chatSuccessfullyLinked) {
            log.info("User " + chat.getId() + " has successfully linked a chat " + group.getGroupName());
            absSender.execute(SendMessage.builder()
                    .text(Replies.CHAT_SUCCESSFULLY_LINKED.text.formatted(group.getGroupName()))
                    .chatId(chat.getId())
                    .build());
        } else
            log.warn("User " + chat.getId() + " has failed to add a chat via /start. his payload: " + payload);
    }


    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "(re)Start the bot!";
    }
}
