package com.ludeng.july.factorytests.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * @Author chengq
 * @Version 1.0
 * @Email chengq@chenyee.com
 * @Time 10:05
 */
public class SPreference {

    public static final String OLDTEST_SPRE_TASKSTART = "oldtest.spre.task.start";
    public static final String OLDTEST_SPRE_LOWPOSHUT = "oldtest.spre.lowpower.shutdown";
    public static final String OLDTEST_SPRE_USBPLUGTI = "oldtest.spre.usbplug.timeout";
    public static final String OLDTEST_SPRE_CHARUSULT = "oldtest.spre.charge.result";

    private static final String OLDTEST_SPRE_FILENAMET = "oldtest.spre.filename";




    private SPreference(){
        throw new IllegalAccessError("Can not be create") ;
    }

    public static boolean putString(Context context, String key, String value) {
        return getSPreEdit(context).putString(key,value).commit();
    }


    public static String getString(Context context, String key, String defaultValue) {
        return getSPreferences(context).getString(key,defaultValue);
    }


    public static boolean putInt(Context context, String key, int value) {
        return getSPreEdit(context).putInt(key,value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSPreferences(context).getInt(key,defValue);

    }

    public static boolean putLong(Context context, String key, long value) {
        return getSPreEdit(context).putLong(key,value).commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSPreferences(context).getLong(key,defValue);
    }


    public static boolean putBoolean(Context context, String key, boolean value) {
        return getSPreEdit(context).putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSPreferences(context).getBoolean(key,defValue);
    }

    public static boolean clearSPreference(Context context){
        return getSPreEdit(context).clear().commit();
    }


    private static SharedPreferences mSPre;
    private static Editor mSPreEditor;
    private static SharedPreferences getSPreferences(Context context) {
        if (null == mSPre) {
            mSPre = context.getSharedPreferences(OLDTEST_SPRE_FILENAMET,
                    Context.MODE_PRIVATE);
        }
        return mSPre;
    }

    private static Editor getSPreEdit(Context context) {
        if (null == mSPreEditor) {
            mSPreEditor = getSPreferences(context).edit();
        }
        return mSPreEditor;
    }

}
