package com.ludeng.july.factorytests.broadcast;

import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ludeng.july.factorytests.utils.DswLog;
import com.ludeng.july.factorytests.utils.SPreference;
import com.ludeng.july.factorytests.utils.ToolsUtil;
import com.ludeng.july.factorytests.model.MiBatteryInfo;
import com.ludeng.july.factorytests.model.Pig;
import com.ludeng.july.factorytests.present.MiContract;

import java.lang.ref.WeakReference;

public class Mi2BroadCast extends BroadcastReceiver {
    private static final String TAG = "Mi2BroadCast";

    private WeakReference<MiContract.RecieveListen>weakReference;
    private String name;

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
            onCallBack(action, (Pig) null);
        }else if (action.equals(ToolsUtil.OLDTEST_ACTION_STOPITEM)) {
            onCallBack(action, (Pig) null);
        }else if (action.equals(ToolsUtil.OLDTEST_ACTION_CHARGEED)) {
            MiBatteryInfo miBatteryInfo = new MiBatteryInfo();
            onBatteryInfo(miBatteryInfo,intent);
            onCallBack(action, miBatteryInfo);
        }else if (action.equals(ToolsUtil.OLDTEST_ACTION_CHARSTOP)) {
            onCallBack(action, (MiBatteryInfo) null);
        }
    }



    private void onCallBack(String action,Pig pig) {
        DswLog.i(TAG,"onCallBack "+action);
        if (weakReference.get() != null) {
            MiContract.RecieveListen<Pig> recieveListen = weakReference.get();
            recieveListen.doRecieve(action, pig);
        }
    }

    private void onCallBack(String action,MiBatteryInfo bInfo) {
        DswLog.i(TAG,"onCallBack "+action);
        if (weakReference.get() != null) {
            MiContract.RecieveListen<MiBatteryInfo> recieveListen = weakReference.get();
            recieveListen.doRecieve(action, bInfo);
        }
    }

    private void onBatteryInfo(MiBatteryInfo miBInfo, Intent intent) {

        miBInfo.setStatus(intent.getIntExtra("status", 0));
        miBInfo.setHealth(intent.getIntExtra("health", 0));
        miBInfo.setPresent(intent.getBooleanExtra("present", false));
        miBInfo.setLevel(intent.getIntExtra("level", 0));
        miBInfo.setScale(intent.getIntExtra("scale", 0));
        miBInfo.setIcon_small(intent.getIntExtra("icon-small", 0));
        miBInfo.setPlugged(intent.getIntExtra("plugged", 0));
        miBInfo.setVoltage(intent.getIntExtra("voltage", 0));
        miBInfo.setTemperature(intent.getIntExtra("temperature", 0));
        miBInfo.setTechnology(intent.getStringExtra("technology"));
        miBInfo.setContent();
        miBInfo.setChargePass();
    }
}
