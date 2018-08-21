package com.ludeng.july;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
