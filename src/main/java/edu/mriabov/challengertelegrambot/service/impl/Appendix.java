package edu.mriabov.challengertelegrambot.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Appendix {
    USER_APPENDIX("user"),
    CHAT_APPENDIX("chat"),
    CHALLENGE_APPENDIX("challenge"),
    WEEKS_APPENDIX("weeks"),
    SKIP_APPENDIX("skip");
    private final String text;

}
