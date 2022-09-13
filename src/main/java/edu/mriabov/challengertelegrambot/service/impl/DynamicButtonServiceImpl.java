package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.privatechat.cache.ChallengePageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.privatechat.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.privatechat.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class DynamicButtonServiceImpl implements DynamicButtonsService {

    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final ChallengePageCache challengePageCache;

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, Appendix appendix) {
        return switch (appendix) {
            case USER_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.USER_APPENDIX.getText(), userPageCache.getPageAmount(chatID));
            case CHAT_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.CHAT_APPENDIX.getText(), chatPageCache.getPageAmount(chatID));
            case CHALLENGE_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.CHALLENGE_APPENDIX.getText(),challengePageCache.getPageAmount(chatID));
        };
    }
}
