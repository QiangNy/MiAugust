package com.ludeng.july.factorytests.model.dbdata;

import android.content.Context;

import com.ludeng.july.factorytests.Utils.DswLog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DbDatabaseManager {
    static String TAG = "DbDatabaseManager";
    private static DbDatabaseHelper sDataBaseHelper;

    public static boolean initDatabase(Context context) {
        createDataBase(context);
        return true;
    }

    private static void createDataBase(Context context) {
        if (sDataBaseHelper == null) {
            DswLog.i(TAG, "createDataBase");
            sDataBaseHelper = new DbDatabaseHelper(context);
        }
    }

    /**
     * insert new issue data
     * 
     * @param missue
     * 
     * @return
     */
    public static long insertAllowIssue(OldTestIssue missue) {
        DswLog.i(TAG, "oldDatabaseManager insertTP");
        return sDataBaseHelper.insertAllowIssue(missue);
    }

    /**
     * delete one issue data
     * 
     * @param time
     * @param scene
     * @return
     */
    public static boolean deleteAllowIssueByTimeAndScene(String time, String scene) {
        DswLog.i(TAG, "issueDatabaseManager deleteTPBytime:" + "time=" + time + ";scene=" + scene);
        return sDataBaseHelper.deleteAllowIssueByTimeAndScene(time, scene);
    }

    /**
     * delete all issues
     * 
     * @return
     */
    public static boolean deleteAllIssue() {
        DswLog.i(TAG, "issueDatabaseManager deleteAllTPException");
        return sDataBaseHelper.deleteAllOldtestIssue();
    }

    public static List<OldTestIssue> queryAllIssues() {
        DswLog.i(TAG, "issueDatabaseManager queryAllissue");
        return sDataBaseHelper.queryAllOldTestIssue();
    }

    public static List<OldTestIssue> queryOldTestBytesttime(String starttestTime) {
        DswLog.i(TAG, "queryTPExceptionsBytesttime ");
        return sDataBaseHelper.queryOldtestBytesttime(starttestTime);
    }

    /*
     * query issue by starttime
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<String> queryIssueStarttime() {
        @SuppressWarnings("rawtypes")
        TreeSet dataIssue = new TreeSet<String>();
        TreeSet result = new TreeSet<String>();
        List<OldTestIssue> issue = queryAllIssues();
        List<OldTestResult> oldresult = DbResultDatabaseManager.queryAllIResult();
        if (issue != null && !issue.isEmpty()) {
            for (int i = 0; i < issue.size(); i++) {
                DswLog.i(TAG, "starttime=" + issue.get(i).getStarttime());
                if (issue.get(i).getStarttime() != null) {
                    dataIssue.add(issue.get(i).getStarttime());
                }
            }
        }
        if (oldresult != null && !oldresult.isEmpty()) {
            for (int i = 0; i < oldresult.size(); i++) {
                result.add(oldresult.get(i).getTestTimeString());
            }
        }
        dataIssue.addAll(result);
        return new ArrayList<String>(dataIssue);
    }
}
