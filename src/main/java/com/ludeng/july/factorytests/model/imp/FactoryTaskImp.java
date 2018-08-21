package com.ludeng.july.factorytests.model.imp;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class FactoryTaskImp<T> implements IUserPiz.task<T> {

    private static final String TAG = FactoryTaskImp.class.getName();

    private T mVar;
    private boolean isCancel;
    private WeakReference<MiContract.Presenter> weakReference;
    MiContract.Presenter mPresenter;



    public FactoryTaskImp(MiContract.Presenter mPresenter, T mVar) {

        this.weakReference = new WeakReference<>(mPresenter);
        mPresenter = (MiContract.Presenter) weakReference.get();
        this.mVar = mVar;
        this.isCancel = false;
    }

    @Override
    public void start(Pig user) {
        MyRunnable runnable =  new MyRunnable<T>(this,mVar);

    }

    @Override
    public void stop(Pig user) {


    }


    @Override
    public T getTaskName() {
        return this.mVar;
    }

    @Override
    public void setTaskName(T var) {
        this.mVar = var;

    }



    private synchronized void doSomeThing(T var) {
        if (mPresenter == null) {

            mPresenter = weakReference.get();
        }


        if (mVar.toString().equals("1")) {

        }
        //timeout
        Log.i(TAG, "the class is hh");
        mPresenter.onStopTask();


        SystemClock.sleep(3000);
        mPresenter.isDone(new Pig());
    }


    static class MyRunnable<T> extends AsyncTask {
        private WeakReference<FactoryTaskImp> weakReference;
        private T var;

        public MyRunnable(FactoryTaskImp weakReference, T var) {
            this.weakReference = new WeakReference<>(weakReference);
            this.var = var;

        }


        @Override
        protected Object doInBackground(Object[] objects) {

            FactoryTaskImp view = (FactoryTaskImp) weakReference.get();
            if (view == null) {
                return null;
            }

            Log.i(TAG, "the class is dddddddddddddd");

            if (isCancelled() || view.isCancel) {
                Log.i(TAG, "the class is cc");
                return null;
            }

            if (!view.isCancel) {
                view.doSomeThing(this.var);
            }
            return null;
        }



    }


}
