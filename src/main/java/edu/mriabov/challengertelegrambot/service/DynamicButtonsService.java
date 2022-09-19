package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.service.impl.Appendix;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface DynamicButtonsService {

    ReplyKeyboardMarkup createMarkup(long chatID, Appendix appendix);
    ReplyKeyboardMarkup createMarkup(long chatID, String appendix);

}
