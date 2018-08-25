package com.ludeng.july.factorytests.utils;

import android.content.Context;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    private Context mContext;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
