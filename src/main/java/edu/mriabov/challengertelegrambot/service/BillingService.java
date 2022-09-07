package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;

import java.util.List;

public interface BillingService {

    boolean isEnoughCoins(long userID, int coinCount);
    List<Boolean> isEnoughCoins(long userID, int[] coinCount);
    boolean billCoins(long userID, int coinCount);
    int challengePrice(Challenge challenge);



}
