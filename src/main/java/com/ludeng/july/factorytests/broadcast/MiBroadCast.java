package com.ludeng.july.factorytests.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.SPreference;
import com.ludeng.july.factorytests.Utils.ToolsUtil;
import com.ludeng.july.factorytests.model.Pig;

public class MiBroadCast extends BroadcastReceiver{

    private static final String TAG = "MiBroadCast";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action == null) {
            DswLog.i(TAG,"the action is null");
            return;
        }

        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            boolean isOldTaskStart = SPreference.getBoolean(context, SPreference.OLDTEST_SPRE_TASKSTART,false);

            DswLog.i(TAG, "ACTION_BOOT_COMPLETED isOldTestStart=" + isOldTaskStart
                    +" boottime="+ SystemClock.elapsedRealtime());

            if (isOldTaskStart) {
                clearTaskCache(context);

                //todo
                /*Intent serviceIntent = new Intent(context, RebootService.class);
                serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(serviceIntent);*/
            } else {
                System.exit(0);
            }
        }

            
    }

    private void clearTaskCache(Context mContext) {
        DswLog.i(TAG,"clearTaskCache");
        SPreference.clearSPreference(mContext);
    }
}
