package edu.mriabov.challengertelegrambot.service;

import edu.mriabov.challengertelegrambot.dao.model.Challenge;

public interface BillingService {

    boolean isEnoughCoins(long userID, int coinCount);

    boolean billCoins(long userID, int coinCount);

    int challengePrice(Challenge challenge);

    boolean isEnoughCoinsForChallenge(Challenge challenge);


}
