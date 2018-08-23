package com.ludeng.july.factorytests.model.dbdata;

import android.content.Context;

import com.ludeng.july.factorytests.Utils.DswLog;

import java.util.ArrayList;
import java.util.List;

public class DbResultDatabaseManager {
	static String TAG = "DbResultDatabaseManager";
	private static DbOldTestResultHelper sResultHelper;

	public static boolean initDatabase(Context context) {
		createDataBase(context);
		return true;
	}

	private static void createDataBase(Context context) {
		if (sResultHelper == null) {
		    DswLog.i(TAG, TAG + " createDataBase");
			sResultHelper = new DbOldTestResultHelper(context);
		}
	}

	/**
	 * 插入一条新的result记录到数据库
	 * 
	 * @param result
	 * 
	 * @return
	 */
	public static long insertAllowResult(OldTestResult result) {
	    DswLog.i(TAG, "oldresultDatabaseManager insertTP");
		return sResultHelper.insertAllowResult(result);

	}

	/**
	 * 删除特定的result记录
	 * 
	 * @param time
	 * @return
	 */
	public static boolean deleteAllowResultByTime(String time) {
	    DswLog.i(TAG, "ResultDatabaseManager deleteTPBytime");
		return sResultHelper.deleteAllowResultByTime(time);
	}

	/**
	 * 删除所有的记录
	 * 
	 * @return
	 */
	public static boolean deleteAllResult() {
	    DswLog.i(TAG, "ResultDatabaseManager deleteAllTPException");
		return sResultHelper.deleteAllOldtestResult();
	}

	/**
	 * 查找所有的result
	 * 
	 * @return
	 */
	public static List<OldTestResult> queryAllIResult() {
	    DswLog.i(TAG, "ResultDatabaseManager queryAllissue");
		return sResultHelper.queryAllOldTestResult();
	}

	public static List<OldTestResult> queryResultBytesttime(String starttestTime) {
		DswLog.i(TAG, "queryResultBytesttime starttestTime="+starttestTime);
		return sResultHelper.queryOldtestResultBytesttime(starttestTime);
	}
	
	/*
	 * 查询Result数据库开始时间字段
	 */
	public static  ArrayList<String> queryResult() {
		ArrayList<String> dataResult = new ArrayList<String>();
		List<OldTestResult> result = queryAllIResult();
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				dataResult.add(result.get(i).getTestTimeString());
			}
		}
		return dataResult;
	}
}
