package edu.mriabov.challengertelegrambot.command;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.MessageSenderService;
import edu.mriabov.challengertelegrambot.utils.TelegramUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public final class OnStartCommandImpl implements Command {

    private final UserRepository userRepository;
    private final MessageSenderService messageSenderService;


    @Override
    public String alias() {
        return "/start";
    }

    @Override
    public void execute(Update update) {
        if (userRepository.existsByTelegramId(update.getMessage().getSenderChat().getId())) {
            TelegramUtils.ArrayToReplyMarkup(Buttons.MAIN_MENU.getKeyboard());
        } else {
            messageSenderService.sendMessage(update.getMessage().getChatId(),Buttons.ON_START_NEW_USER);
        }
    }


}
