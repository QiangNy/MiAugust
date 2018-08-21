package com.ludeng.july.factorytests.model.imp;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class FactoryTaskImp<T> implements IUserPiz.task<T> {

    private static final String TAG = "chenguang";

    private T mVar;
    private boolean isTaskStart = false;
    private WeakReference<MiContract.Presenter> weakReference;
    private MiContract.Presenter mPresenter;
    private MRunnable<T> mRunnable;
    private Pig taskPig;



    public FactoryTaskImp(MiContract.Presenter mPresenter, T mVar) {

        this.weakReference = new WeakReference<>(mPresenter);

        this.mVar = mVar;
    }

    @Override
    public void start(Pig pig) {

        if (isTaskStart) {
            return;
        }
        taskPig = pig;
        Log.i(TAG, "FactoryTaskImp class start is Tid"+ Thread.currentThread().getId());
        mRunnable = new MRunnable<T>(FactoryTaskImp.this,mVar);
        mRunnable.setOnLinstenner(this);
        mRunnable.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        isTaskStart = true;
    }

    @Override
    public void stop(Pig mPig) {
        mRunnable.cancel(true);
    }


    @Override
    public T getTaskName() {
        return this.mVar;
    }



    @Override
    public void setTaskName(T var) {
        this.mVar = var;
    }



    @Override
    public void taskFinished(boolean isStop) {
        Log.i(TAG, "FactoryTaskImp class finished is Tid"+ Thread.currentThread().getId());
        mRunnable.setOnLinstenner(null);

        isTaskStart = false;

        if (weakReference == null) {
            Log.i(TAG, "FactoryTaskImp weakReference is null");
            return;
        }

        mPresenter = weakReference.get();
        taskPig.setModelTaskStop(true);
        mPresenter.isDone(taskPig);
    }

    @Override
    public void onDestroy() {
        mRunnable = null;
        mPresenter = null;
    }

}
