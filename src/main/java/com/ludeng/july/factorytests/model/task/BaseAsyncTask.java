package com.ludeng.july.factorytests.model.task;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.broadcast.Mi2BroadCast;
import com.ludeng.july.factorytests.model.IUserPiz;
import com.ludeng.july.factorytests.present.MiContract;

import junit.framework.Test;

public class BaseAsyncTask extends AsyncTask implements IUserPiz.taskListenner,MiContract.RecieveListen{

    public BaseAsyncTask() {
        IntentFilter chargeFilter = new IntentFilter();
        chargeFilter.addAction(ToolsUtil.OLDTEST_ACTION_STOPITEM);
        Singleton.getInstance().getmContext().registerReceiver(new Mi2BroadCast(BaseAsyncTask.this), chargeFilter);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRecieve(String action) {
        //stop task
        if(ToolsUtil.OLDTEST_ACTION_STOPITEM.equals(action)) {
            cancel(true);
        }
    }
}
