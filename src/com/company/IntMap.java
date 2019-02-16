package com.company;

public interface IntMap {
    void clear();
    boolean containsKey(int key);
    boolean containsValue(int value);
    int get(int key);
    boolean isEmpty();
    int put(int key, int value);
    int remove(int key);
    int size();
}
