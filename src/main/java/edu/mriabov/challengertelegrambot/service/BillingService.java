package edu.mriabov.challengertelegrambot.service;

import java.util.List;

public interface BillingService {

    boolean isEnoughCoins(long chatID,int coinCount);
    List<Boolean> isEnoughCoins(long chatID, int[] coinCount);
    boolean billCoins(long chatID, int coinCount);

}
