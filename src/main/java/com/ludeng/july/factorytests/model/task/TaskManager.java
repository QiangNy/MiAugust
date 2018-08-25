package com.ludeng.july.factorytests.model.task;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;
import com.ludeng.july.factorytests.model.task.BaseAsyncTask;
import com.ludeng.july.factorytests.model.task.Flashlight;
import com.ludeng.july.factorytests.utils.DswLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TaskManager implements IUserPiz.taskListenner {



    public String TAG = "TaskManager";
    private ArrayList arrayList = new ArrayList();

    public void setOnLinstenner(IUserPiz.task onLinstenner) {
        this.onLinstenner = onLinstenner;
    }

    private IUserPiz.task onLinstenner;

    public TaskManager() {

    }


    public void startTask(Pig pig) {
        doSomeThing(pig);
    }


/*    public void cancelled() {
        //do some cancel

    }*/


    private synchronized void doSomeThing(Pig taskPig) {

        switch (taskPig.getGroupID()) {
            case 0:
                Flashlight flashlight = new Flashlight(0,this);
                flashlight.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                Flashlight flashlight1 = new Flashlight(1,this);
                flashlight1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;

            case 1:
                Flashlight flashlight5 = new Flashlight(2,this);
                flashlight5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                Flashlight flashlight6 = new Flashlight(3,this);
                flashlight6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
               break;

            case 2:
                Flashlight flashlight2 = new Flashlight(4,this);
                flashlight2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }

    }


    @Override
    public void onStart(int clazID) {
        DswLog.i(TAG, "onStart clazID="+clazID);
        arrayList.add( Integer.toHexString(hashCode()));
    }

    @Override
    public void onStop(int clazID) {
        arrayList.remove(Integer.toHexString(hashCode()));

        DswLog.i(TAG, "onStop clazID="+clazID);
        if (arrayList.isEmpty()) {
            DswLog.i(TAG, "onStop finishTask");
            onLinstenner.finishTask(false);
        }

    }
}
