package com.ludeng.july.factorytests.model.imp;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;

import java.lang.ref.WeakReference;

public class MRunnable<T> extends AsyncTask {



    private WeakReference<FactoryTaskImp> weakReference;
    private T var;
    private final String TAG = "chenguang";

    public void setOnLinstenner(IUserPiz.task onLinstenner) {
        this.onLinstenner = onLinstenner;
    }

    private IUserPiz.task onLinstenner;


    public MRunnable(FactoryTaskImp weakReference, T var) {
        this.weakReference = new WeakReference<>(weakReference);
        this.var = var;


    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Log.i(TAG, "MRunnable class doInBackground is Tid"+ Thread.currentThread().getId());

        FactoryTaskImp view = (FactoryTaskImp) weakReference.get();
        if (view == null) {
            return null;
        }

        Log.i(TAG, "the class is dddddddddddddd");


        doSomeThing(this.var);
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.i(TAG, "MRunnable class onCancelled is Tid"+ Thread.currentThread().getId());

        //if factorytest time out broadcast coming
        // onLinstenner.taskFinished(true);
        //todo
        onLinstenner.taskFinished(false);
    }


    private synchronized void doSomeThing(T var) {


        while (!isCancelled()) {

            SystemClock.sleep(2000);

            Log.i(TAG, "the class is hh");
        }

      /*  if (mVar.toString().equals("1")) {

        }
        //timeout
        Log.i(TAG, "the class is hh");
        mPresenter.onStopTask();


        SystemClock.sleep(3000);
        mPresenter.isDone(new Pig());*/
    }

}
