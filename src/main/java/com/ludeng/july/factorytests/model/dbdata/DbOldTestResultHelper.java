package com.ludeng.july.factorytests.model.dbdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbOldTestResultHelper extends DbDatabaseHelper {

	public DbOldTestResultHelper(Context context) {
		super(context);

	}

	/**
	 * 插入一条新的issue数据
	 * 
	 * @param result
	 * @return
	 */
	public long insertAllowResult(OldTestResult result) {
		Log.i(TAG, "DatabaseHelper insertresult----"+result.getIsTestDone());
		ContentValues cv = new ContentValues();
		cv.put(Constant.COLUMN_NAME_TESTTIME, result.getTestTimeString());
		cv.put(Constant.COLUMN_NAME_ENDTIME, result.getEndtimeString());
		cv.put(Constant.COLUMN_NAME_ISTESTDONE, result.getIsTestDone());
		cv.put(Constant.COLUMN_NAME_DURATION, result.getDurationTimeString());
		long row = mDb.insert(Constant.RESULT_TABLE_NAME, null, cv);
		Log.i(TAG, "row=" + row);
		return row;
	}

	/**
	 * 删除特定的result记录
	 * 
	 * @param time
	 * @param time
	 * @return
	 */
	public boolean deleteAllowResultByTime(String time) {
		Log.i(TAG, "TPDatabaseHelper deletetpException");
		String where = Constant.COLUMN_NAME_TESTTIME + "= ?";
		String[] whereValue = new String[] { time };
		return mDb.delete(Constant.RESULT_TABLE_NAME, where, whereValue) > 0;
	}

	/**
	 * 删除所有的result记录
	 * 
	 * @return
	 */
	public boolean deleteAllOldtestResult() {
		Log.i(TAG, "AllowTPDatabaseHelper deleteAllissue");
		return mDb.delete(Constant.RESULT_TABLE_NAME, null, null) >= 0;
	}

	/*
	 * 查询特定时间的Result记录
	 */
	public List<OldTestResult> queryOldtestResultBytesttime(String starttestTime) {
		Log.i("aiyong", "query oldtestexceptions by test time");
		String where = Constant.COLUMN_NAME_TESTTIME + "= ?";
		String[] whereValue = new String[] { starttestTime };
		Cursor query = null;
		List<OldTestResult> OldTestResultList = null;
		try {
			query = mDb.query(Constant.RESULT_TABLE_NAME, null, where, whereValue,
					null, null, Constant.ORDER_BY);
			if (query != null && query.getCount() > 0) {
				Log.i(TAG, "query oldtestexceptions by test time is not null");
				OldTestResultList = new ArrayList<OldTestResult>();
				while (query.moveToNext()) {
					String testtime = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_TESTTIME));
					String endtime = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ENDTIME));
					String isdone = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ISTESTDONE));
					String time_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_DURATION));
					Log.i(TAG, "query result db testtime="+testtime+" endtime="+endtime+" isdone="+isdone+" time_="+time_);
					OldTestResult mResult = new OldTestResult();
					mResult.setTestTimeString(testtime);
					mResult.setEndtimeString(endtime);
					if(isdone.equals("1")){
						mResult.setIsTestDone(true);
					}else{
						mResult.setIsTestDone(false);
					}
					mResult.setDurationTimeString(time_);
					OldTestResultList.add(mResult);
				}
			}
		} finally {
			if (query != null) {
				query.close();
			}
		}
		return OldTestResultList;

	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<OldTestResult> queryAllOldTestResult() {
		Log.i(TAG, "queryAll issue");
		Cursor query = null;
		List<OldTestResult> mResultList = null;
		try {
			query = mDb.query(Constant.RESULT_TABLE_NAME, null, null, null, null,
					null, Constant.ORDER_BY);
			if (query != null && query.getCount() > 0) {
				Log.i(TAG, "AllowPermDatabaseHelper queryAllAllowIssue is not null");
				mResultList = new ArrayList<OldTestResult>();
				while (query.moveToNext()) {
					String starttime_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_TESTTIME));
					String endtime = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ENDTIME));
					String isdone = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ISTESTDONE));
					String time_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_DURATION));

					OldTestResult mResult = new OldTestResult();
					mResult.setTestTimeString(starttime_);
					mResult.setEndtimeString(endtime);
					mResult.setIsTestDone(Boolean.valueOf(isdone));
					mResult.setDurationTimeString(time_);

					mResultList.add(mResult);
				}
			}
		} finally {
			if (query != null) {
				query.close();
			}
		}
		return mResultList;
	}
}
