package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.dao.repository.ChatRepository;
import edu.mriabov.challengertelegrambot.dao.repository.UserRepository;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import edu.mriabov.challengertelegrambot.utils.ButtonsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class DynamicButtonServiceImpl implements DynamicButtonsService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, Appendix appendix) {
        return switch (appendix) {
            case USER_APPENDIX -> ButtonsUtils.createDynamicMarkup(Appendix.USER_APPENDIX.getText(), userRepository.countChatsById(chatID));
            case CHAT_APPENDIX -> ButtonsUtils.createDynamicMarkup(Appendix.CHAT_APPENDIX.getText(), chatRepository.countUsersByTelegramID(chatID));
        };
    }
}
