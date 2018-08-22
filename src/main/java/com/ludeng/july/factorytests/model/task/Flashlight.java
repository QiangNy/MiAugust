package com.ludeng.july.factorytests.model.task;

import android.os.SystemClock;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;
import com.ludeng.july.factorytests.model.MRunnable;

import java.util.TimerTask;

public class Flashlight extends MRunnable {


    public Flashlight() {
        this.TAG = "Flashlight";
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        DswLog.i(TAG,"Cancel");
    }



    @Override
    protected Object doInBackground(Object[] objects) {

        while (!isCancelled()) {
            SystemClock.sleep(3000);
            DswLog.i(TAG, "is excuting");
        }
        return null;
    }
}
