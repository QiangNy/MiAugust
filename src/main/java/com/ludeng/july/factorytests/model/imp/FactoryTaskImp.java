package com.ludeng.july.factorytests.model.imp;

import android.content.Intent;
import android.os.AsyncTask;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.MRunnable;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.task.DBAsyncTask;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class FactoryTaskImp<T> implements IUserPiz.task<T> {

    private static final String TAG = "chenguang";

    private T mVar;
    private boolean isTaskStart = false;
    private WeakReference<MiContract.Presenter> weakReference;
    private MiContract.Presenter mPresenter;
    private MRunnable mRunnable;

    public Pig getTaskPig() {
        return taskPig;
    }

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
        DswLog.i(TAG, "FactoryTaskImp class start is Tid"+ Thread.currentThread().getId());
        mRunnable = new MRunnable(FactoryTaskImp.this);
        mRunnable.setOnLinstenner(this);
        mRunnable.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        isTaskStart = true;

        final DBAsyncTask dbAsyncTask = new DBAsyncTask();
        dbAsyncTask.execute(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void stop(Pig mPig) {
        if (mRunnable != null)
            mRunnable.cancel(true);

        DswLog.i(TAG,"sendbroad OLDTEST_ACTION_STOPITEM");
        Singleton.getInstance().getmContext().sendBroadcast(new Intent(ToolsUtil.OLDTEST_ACTION_STOPITEM));
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
    public void finishTask(boolean success) {
        mRunnable.setOnLinstenner(null);

        isTaskStart = false;

        if (weakReference.get() != null) {
            mPresenter = weakReference.get();
            taskPig.setModelTaskStop(true);
            mPresenter.onDone(taskPig);
        }
    }

    @Override
    public void destroyTask() {
        mRunnable = null;
        mPresenter = null;
        weakReference = null;
        DswLog.i(TAG,"model clear");
    }

}
