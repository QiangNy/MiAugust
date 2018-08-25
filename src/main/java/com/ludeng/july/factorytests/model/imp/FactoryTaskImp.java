package com.ludeng.july.factorytests.model.imp;

import android.content.Intent;
import android.os.AsyncTask;

import com.ludeng.july.factorytests.utils.DswLog;
import com.ludeng.july.factorytests.utils.Singleton;
import com.ludeng.july.factorytests.utils.ToolsUtil;
import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.task.TaskManager;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class FactoryTaskImp<T> implements IUserPiz.task<T> {

    private static final String TAG = "chenguang";

    private T mVar;
    private boolean isTaskStart = false;
    private WeakReference<MiContract.Presenter> weakReference;
    private MiContract.Presenter mPresenter;
    private TaskManager taskManager;

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

        if (taskManager == null) {
            taskManager = new TaskManager();
            taskManager.setOnLinstenner(this);
        }

        taskManager.startTask(pig);
        isTaskStart = true;
    }

    @Override
    public void stop(Pig mPig) {
      /*  if (taskManager != null)
            taskManager.cancelled();*/

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

        isTaskStart = false;

        if (weakReference.get() != null) {
            mPresenter = weakReference.get();
            taskPig.setModelTaskStop(true);
            mPresenter.onDone(taskPig);
        }
    }

    @Override
    public void destroyTask() {
        taskManager.setOnLinstenner(null);
        taskManager = null;
        mPresenter = null;
        weakReference = null;
        DswLog.i(TAG,"model clear");
    }

}
