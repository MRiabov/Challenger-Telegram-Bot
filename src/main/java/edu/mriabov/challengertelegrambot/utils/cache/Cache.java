package edu.mriabov.challengertelegrambot.utils.cache;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean contains(K key);

}
