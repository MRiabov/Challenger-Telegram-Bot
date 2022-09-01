package edu.mriabov.challengertelegrambot.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Appendix{
    USER_APPENDIX("user"),
    CHAT_APPENDIX("chat");
    private final String text;

}
