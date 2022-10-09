package edu.mriabov.challengertelegrambot.service.impl;

import edu.mriabov.challengertelegrambot.cache.impl.ChallengePageCache;
import edu.mriabov.challengertelegrambot.cache.impl.ChatPageCache;
import edu.mriabov.challengertelegrambot.cache.impl.UserPageCache;
import edu.mriabov.challengertelegrambot.service.DynamicButtonsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static edu.mriabov.challengertelegrambot.service.impl.Appendix.CHALLENGE_APPENDIX;
import static edu.mriabov.challengertelegrambot.service.impl.Appendix.WEEKS_APPENDIX;
import static edu.mriabov.challengertelegrambot.utils.ButtonsMappingUtils.createDynamicMarkup;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicButtonServiceImpl implements DynamicButtonsService {

    private final ChatPageCache chatPageCache;
    private final UserPageCache userPageCache;
    private final ChallengePageCache challengePageCache;

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, Appendix appendix) {
        log.info("Dynamic ReplyKeyboardMarkup is created with appendix \"" + appendix.getText() + '"');
        return switch (appendix) {
            case USER_APPENDIX -> createDynamicMarkup(Appendix.USER_APPENDIX.getText(), userPageCache.getPageAmount(chatID));
            case CHAT_APPENDIX -> createDynamicMarkup(Appendix.CHAT_APPENDIX.getText(), chatPageCache.getPageAmount(chatID));
            case CHALLENGE_APPENDIX -> createDynamicMarkup(CHALLENGE_APPENDIX.getText(), challengePageCache.getPageAmount(chatID));
            case WEEKS_APPENDIX -> createDynamicMarkup(WEEKS_APPENDIX.getText(), 9);
            case SKIP_APPENDIX -> createDynamicMarkup(Appendix.SKIP_APPENDIX.getText(), challengePageCache.getPageAmount(chatID));
        };
    }

    @Override
    public ReplyKeyboardMarkup createMarkup(long chatID, String appendix) {
        return createMarkup(chatID, Appendix.valueOf((appendix + "_appendix").toUpperCase()));
    }
}
