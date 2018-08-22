package com.ludeng.july.factorytests.Utils;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();



    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
