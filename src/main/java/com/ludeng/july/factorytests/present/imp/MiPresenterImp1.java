package com.ludeng.july.factorytests.present.imp;

import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.Task1Activity;
import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;

import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


public class MiPresenterImp1 implements MiContract.Presenter {
    private FactoryTaskImp mModel;
    private WeakReference<MiContract.View> weakReference;
    private final String TAG = "chenguang";
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
    public void isDone(Pig pig) {
        Log.i(TAG, "MiPresenterImp1 class is Tid"+ Thread.currentThread().getId());

        this.mPig = pig;

        //judge is timeout
        if (weakReference != null) {
            mView = (Task1Activity) weakReference.get();
            mView.isTimeOut();
        }
    }

    @Override
    public void onStopTask(Pig mPig) {
        if (mModel != null && !this.mPig.isModelTaskStop())
            mModel.stop(mPig);
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
        mModel = null;
        stopTimer();
    }

    @Override
    public void onTimeOut(boolean isTimeOut) {

        //timeout
        if (isTimeOut) {
            mPig.setTimeTaskStop(true);
            DswLog.i(TAG, "time is out,finishi the task");

            //refresh UI
            Task1Activity mView = (Task1Activity) weakReference.get();
            mView.refreshUI(this.mPig);
            //todo
        }else {
            //choose next group
            startGroup(this.mPig.getnextGroupID());
        }
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
            if ( weakReference!=null ) {
                mContext = weakReference.get();
            }

            while (!mContext.mPig.isTimeTaskStop()) {
                Log.i(mContext.TAG, "TimerTask class is Tid"+ Thread.currentThread().getId());

                SystemClock.sleep(6000);

                mContext.onStopTask(mContext.mPig);
            }

            //if timeout the broadcast is coming
            //refresh UI
        }
    }
}
