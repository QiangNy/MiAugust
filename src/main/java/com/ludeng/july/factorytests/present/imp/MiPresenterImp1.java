package com.ludeng.july.factorytests.present.imp;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ludeng.july.factorytests.Task1Activity;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.model.imp.FactoryTaskImp;

import com.ludeng.july.factorytests.model.imp.MRunnable;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


public class MiPresenterImp1 implements MiContract.Presenter {
    private FactoryTaskImp mModel;
    private WeakReference<MiContract.View> weakReference;
    private final String TAG = "chenguang";
   // private Task1Activity mView;
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

        //timeout
        if (pig.isTimeTaskStop()) {
            Log.i(TAG, "MiPresenterImp1 pig.isModelTaskStop true");
        }else {
            //choose next group
            startGroup(pig.getnextGroupID());
        }

        //refresh UI
        Task1Activity mView = (Task1Activity) weakReference.get();
        mView.refreshUI(pig);
    }

    @Override
    public void onStopTask(Pig mPig) {
        if (mModel != null)
            mModel.stop(mPig);
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
        mModel = null;
        stopTimer();
    }


    @Override
    public void startGroup(final int group) {



        mPig.setGroupID(group);

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

                SystemClock.sleep(10000);

                mContext.onStopTask(mContext.mPig);
            }

            //if timeout the broadcast is coming
            //refresh UI
            if (mContext.weakReference != null) {
                Task1Activity mView = (Task1Activity) mContext.weakReference.get();
                mView.refreshUI(mContext.mPig);
            }

        }
    }
}
