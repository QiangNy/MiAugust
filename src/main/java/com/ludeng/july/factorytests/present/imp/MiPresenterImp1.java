package com.ludeng.july.factorytests.present.imp;

import android.os.SystemClock;
import com.ludeng.july.factorytests.Task1Activity;
import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;

import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


public class MiPresenterImp1 implements MiContract.Presenter {
    private FactoryTaskImp mModel;
    private WeakReference<MiContract.View> weakReference;
    private final String TAG = "MiPresenterImp1";
    private Task1Activity mView;
    private Timer mTimer;

    private Pig mPig;
    private MTimeTask mTimeTask;

    public MiPresenterImp1(MiContract.View view, Pig pig) {
        this.weakReference = new WeakReference<>(view);
        this.mPig = pig;

        mModel = new FactoryTaskImp<>(this,mPig);
    }


    @Override
    public void onDone(Pig pig) {

        this.mPig = pig;

        //judge is timeout
        if (weakReference.get() != null) {
            mView = (Task1Activity) weakReference.get();
            mView.doTimeOut();
        }
    }

    @Override
    public void onStopTask(Pig mPig) {
        if (mModel != null && !this.mPig.isModelTaskStop()) {
            DswLog.i(TAG, "onStopTask");
            mModel.stop(mPig);
        }
    }

    @Override
    public void onDestroy() {
        mModel.destroyTask();
        mModel = null;
        stopTimer();
    }

    @Override
    public void onTimeOut(boolean isTimeOut) {

        //timeout
        if (isTimeOut) {
            mPig.setTimeTaskStop(true);
            DswLog.i(TAG, "onTimeOut is true");

            //refresh UI
            if(weakReference.get() != null) {
                Task1Activity mView = (Task1Activity) weakReference.get();
                mView.doFreshUI(this.mPig);
                //todo
            }
        }else {
            //choose next group
            startGroup(this.mPig.getnextGroupID());
        }
    }

    @Override
    public void onDataInit() {


        // set box1 content
        String box1Content = "";

    }


    @Override
    public void startGroup(final int group) {

        DswLog.i(TAG,"startGroup="+group);

        mPig.setGroupID(group);
        mPig.setModelTaskStop(false);
        mModel.start(mPig);

        // 初始化定时器
        if (mTimer == null) {
            mTimer = new Timer();
            mTimeTask = new MTimeTask(this);
            mTimer.schedule(mTimeTask,0);
        }

    }

    // 停止定时器
    private void stopTimer(){
        if(mTimer != null){

            mTimer.cancel();
            // 一定设置为null，否则定时器不会被回收
            mTimer = null;
            mTimeTask = null;
        }
    }

    static class MTimeTask extends TimerTask {
        private WeakReference<MiPresenterImp1> weakReference;
        private MiPresenterImp1 mContext;
        public MTimeTask(MiPresenterImp1 weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void run() {
            if ( weakReference.get() != null ) {
                mContext = weakReference.get();
            }

            while (!mContext.mPig.isTimeTaskStop()) {

                SystemClock.sleep(6000);

                DswLog.i(mContext.TAG, "TimerTask onStopTask");
                mContext.onStopTask(mContext.mPig);
            }
        }
    }
}
