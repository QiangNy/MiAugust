package com.ludeng.july.factorytests.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.SPreference;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class Mi2BroadCast extends BroadcastReceiver {
    private static final String TAG = "Mi2BroadCast";

    private WeakReference<MiContract.RecieveListen> weakReference;

    public Mi2BroadCast(MiContract.RecieveListen recieveListen) {
        this.weakReference = new WeakReference<>(recieveListen);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            DswLog.i(TAG,"the action is null");
            return;
        }

        if (action.equals(ToolsUtil.OLDTEST_ACTION_FINISHED)) {
            clearTaskCache(context,action);
        }
    }

    private void clearTaskCache(Context mContext,String action) {
        DswLog.i(TAG,"clearTaskCache");
        if (weakReference != null) {
            weakReference.get().onRecieve(action);
        }
    }
}
