package edu.mriabov.challengertelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface DynamicButtonsService {

    ReplyKeyboardMarkup createMarkup(String appendix, int pageSize);

}
