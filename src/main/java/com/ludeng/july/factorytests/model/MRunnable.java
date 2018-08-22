package com.ludeng.july.factorytests.model;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;
import com.ludeng.july.factorytests.model.task.Flashlight;

import java.lang.ref.WeakReference;

public class MRunnable extends AsyncTask {



    private WeakReference<FactoryTaskImp> weakReference;

    public String TAG = "MRunnable";

    public void setOnLinstenner(IUserPiz.task onLinstenner) {
        this.onLinstenner = onLinstenner;
    }

    private IUserPiz.task onLinstenner;

    public MRunnable() {

    }

    public MRunnable(FactoryTaskImp weakReference) {
        this.weakReference = new WeakReference<>(weakReference);

    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Log.i(TAG, "MRunnable class doInBackground is Tid"+ Thread.currentThread().getId());

        FactoryTaskImp view = weakReference.get();
        if (view == null) {
            return null;
        }

        while (!isCancelled()) {
            doSomeThing(view.getTaskPig());
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.i(TAG, "MRunnable class onCancelled is Tid"+ Thread.currentThread().getId());

        //todo
        onLinstenner.taskFinished(false);
    }


    private synchronized void doSomeThing(Pig taskPig) {

        switch (taskPig.getGroupID()) {
            case 0:
               /* Flashlight flashlight = new Flashlight();
                flashlight.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
                break;

            case 1:
                break;
            case 2:
                break;
        }
        SystemClock.sleep(3000);
        Log.i(TAG, "the class is hh");


      /*  if (mVar.toString().equals("1")) {

        }
        //timeout
        Log.i(TAG, "the class is hh");
        mPresenter.onStopTask();


        SystemClock.sleep(3000);
        mPresenter.isDone(new Pig());*/
    }

}
