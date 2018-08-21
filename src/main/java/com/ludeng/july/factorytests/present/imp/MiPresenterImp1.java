package com.ludeng.july.factorytests.present.imp;

import android.os.AsyncTask;
import android.util.Log;

import com.ludeng.july.factorytests.Task1Activity;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;

import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;


public class MiPresenterImp1 implements MiContract.Presenter {
    private FactoryTaskImp mFacTask;
    private WeakReference<MiContract.View> weakReference;
    private Task1Activity mView;


    public MiPresenterImp1(MiContract.View view) {
        this.weakReference = new WeakReference<>(view);
        Task1Activity mView = (Task1Activity) weakReference.get();


    }

    @Override
    public void getData(int userId) {

    }

    @Override
    public void checkData() {

    }

    @Override
    public void isDone(Pig pig) {
        Log.i("chengq", "the class is kk");
        mView.refreshUI(pig);
    }

    @Override
    public void onStopTask() {
        mFacTask.cancel(true);
    }


    @Override
    public void startGroup(int group) {
        //which one start
        mFacTask = new FactoryTaskImp(this,""+group);
        mFacTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
