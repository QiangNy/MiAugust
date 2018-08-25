package com.ludeng.july.factorytests.model.task;

import android.content.IntentFilter;
import android.os.AsyncTask;

import com.ludeng.july.factorytests.utils.Singleton;
import com.ludeng.july.factorytests.utils.ToolsUtil;
import com.ludeng.july.factorytests.broadcast.Mi2BroadCast;
import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class BaseAsyncTask extends AsyncTask implements MiContract.RecieveListen<Pig>{

    Mi2BroadCast mi2BroadCast;

    IUserPiz.taskListenner mTaskListen;
    private WeakReference<IUserPiz.taskListenner > weakReference;

    public IUserPiz.taskListenner getTaskListenner() {
        return mTaskListen;
    }

    public void setTaskListenner(IUserPiz.taskListenner taskListenner) {
        this.mTaskListen = taskListenner;
    }

    public BaseAsyncTask(IUserPiz.taskListenner onLinstenner) {
        this.weakReference = new WeakReference<>(onLinstenner);
        setTaskListenner(weakReference.get());
        mTaskListen.onStart(hashCode());

        IntentFilter chargeFilter = new IntentFilter();
        chargeFilter.addAction(ToolsUtil.OLDTEST_ACTION_STOPITEM);
        mi2BroadCast = new Mi2BroadCast(BaseAsyncTask.this);
        Singleton.getInstance().getmContext().registerReceiver(mi2BroadCast, chargeFilter);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }



    @Override
    protected void onCancelled() {
        super.onCancelled();
        mTaskListen.onStop(hashCode());
        mTaskListen = null;
        if (mi2BroadCast != null)
            Singleton.getInstance().getmContext().unregisterReceiver(mi2BroadCast);

    }

    @Override
    public void doRecieve(String action, Pig var) {
        //stop task
        if(ToolsUtil.OLDTEST_ACTION_STOPITEM.equals(action)) {
            cancel(true);
        }
    }
}
