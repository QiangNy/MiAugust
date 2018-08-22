package com.ludeng.july.factorytests.model.task;

import android.os.SystemClock;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;
import com.ludeng.july.factorytests.model.MRunnable;

import java.util.TimerTask;

public class Flashlight extends BaseAsyncTask {


    private static final String TAG = "Flashlight";
    private int id;
    public Flashlight() {

    }

    public Flashlight(int ID) {
        super();
        this.id = ID;
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

    @Override
    public void onPause() {
        super.onPause();
        //暂停
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始
    }


}
