package com.ludeng.july.factorytests.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class ToolsUtil {
    public static String OLDTEST_ACTION_NEXTITEM = "android.chenyee.os.oldtest.next";
    public static String OLDTEST_ACTION_STOPITEM = "android.chenyee.os.oldtest.stopitem";
    public static String OLDTEST_ACTION_FINISHED = "android.chenyee.os.oldtest.gameover";



    public static void setAlarm(Context mContext, String action, float time) {
        Intent intent = new Intent(action);
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerTime = SystemClock.elapsedRealtime() + (long) (time * 60 * 1000);
        am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pending);
    }

    public static void cancelAlarm(Context mContext, String action) {
        AlarmManager mAlarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(action);
        PendingIntent mPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarm.cancel(mPending);
    }
}
