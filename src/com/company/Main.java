package com.company;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        IntMap intMap=new IntHashMap();
        intMap.put(5,50);
        intMap.put(4,80);
        intMap.put(1,60);
        intMap.put(2,7);
        intMap.put(1,60);
        intMap.put(10,47);
        intMap.put(10,45);
        intMap.put(40,50);
        intMap.put(12,7);
        intMap.put(13,45);
        intMap.put(0,4);
        intMap.put(3,3);
        System.out.println(intMap);
        System.out.println(intMap.size());
        System.out.println(intMap.containsKey(20));
        System.out.println(intMap.containsValue(49));
        System.out.println(intMap.get(2));
        intMap.remove(3);
        System.out.println(intMap);
        System.out.println(intMap.size());
        intMap.clear();
        System.out.println(intMap);
        System.out.println(intMap.isEmpty());

    }
}
