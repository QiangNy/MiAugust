package com.ludeng.july.factorytests.model.task;

import android.os.SystemClock;

import com.ludeng.july.factorytests.utils.DswLog;
import com.ludeng.july.factorytests.model.IUserPiz;

public class Flashlight extends BaseAsyncTask {


    private static final String TAG = "Flashlight";
    private int id;


    public Flashlight(int id, IUserPiz.taskListenner taskListenner) {
        super(taskListenner);
        this.id = id;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        DswLog.i(TAG,"Cancel task id="+id);
    }



    @Override
    protected Object doInBackground(Object[] objects) {


        while (!isCancelled()) {
            SystemClock.sleep(3000);
            DswLog.i(TAG, "excuting task id="+id);
        }
        return null;
    }

}
