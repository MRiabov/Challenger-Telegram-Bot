package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.cache.ChallengePageCache;
import edu.mriabov.challengertelegrambot.cache.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.UserPageCache;
import edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicButtonServiceImpl implements DynamicButtonsService {

    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final ChallengePageCache challengePageCache;

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, Appendix appendix) {
        log.info("Dynamic ReplyKeyboardMarkup is created with appendix \""+appendix.getText()+'"');
        return switch (appendix) {
            case USER_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.USER_APPENDIX.getText(), userPageCache.getPageAmount(chatID));
            case CHAT_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.CHAT_APPENDIX.getText(), chatPageCache.getPageAmount(chatID));
            case CHALLENGE_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.CHALLENGE_APPENDIX.getText(),challengePageCache.getPageAmount(chatID));
            case WEEKS_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.WEEKS_APPENDIX.getText(),9);
            case SKIP_APPENDIX -> ButtonsMappingUtils.createDynamicMarkup(Appendix.SKIP_APPENDIX.getText(),challengePageCache.getPageAmount(chatID));
        };
    }

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, String appendix) {
        return createMarkup(chatID, Appendix.valueOf((appendix+"_appendix").toUpperCase()));
    }
}
