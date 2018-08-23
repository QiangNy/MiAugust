package com.ludeng.july.factorytests.model.dbdata;

import static com.cydroid.os.autooldtest.model.Constant.COLUMN_NAME_TESTTIME;
import static com.cydroid.os.autooldtest.model.Constant.COLUMN_NAME_ENDTIME;
import static com.cydroid.os.autooldtest.model.Constant.COLUMN_NAME_ISTESTDONE;
import static com.cydroid.os.autooldtest.model.Constant.COLUMN_NAME_DURATION;
import static com.cydroid.os.autooldtest.model.Constant.ORDER_BY;
import static com.cydroid.os.autooldtest.model.Constant.RESULT_TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import com.cydroid.os.autooldtest.model.Constant;
import com.cydroid.os.autooldtest.model.OldTestIssue;
import com.cydroid.os.autooldtest.model.OldTestResult;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DbOldTestResultHelper extends DbDatabaseHelper {

	public DbOldTestResultHelper(Context context) {
		super(context);

	}

	/**
	 * 插入一条新的issue数据
	 * 
	 * @param permissionModel
	 * @return
	 */
	public long insertAllowResult(OldTestResult result) {
		Log.i(TAG, "DatabaseHelper insertresult----"+result.getIsTestDone());
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME_TESTTIME, result.getTestTimeString());
		cv.put(COLUMN_NAME_ENDTIME, result.getEndtimeString());
		cv.put(COLUMN_NAME_ISTESTDONE, result.getIsTestDone());
		cv.put(COLUMN_NAME_DURATION, result.getDurationTimeString());
		long row = mDb.insert(RESULT_TABLE_NAME, null, cv);
		Log.i(TAG, "row=" + row);
		return row;
	}

	/**
	 * 删除特定的result记录
	 * 
	 * @param time
	 * @param scene
	 * @return
	 */
	public boolean deleteAllowResultByTime(String time) {
		Log.i(TAG, "TPDatabaseHelper deletetpException");
		String where = COLUMN_NAME_TESTTIME + "= ?";
		String[] whereValue = new String[] { time };
		return mDb.delete(RESULT_TABLE_NAME, where, whereValue) > 0;
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
		String where = COLUMN_NAME_TESTTIME + "= ?";
		String[] whereValue = new String[] { starttestTime };
		Cursor query = null;
		List<OldTestResult> OldTestResultList = null;
		try {
			query = mDb.query(RESULT_TABLE_NAME, null, where, whereValue,
					null, null, ORDER_BY);
			if (query != null && query.getCount() > 0) {
				Log.i(TAG, "query oldtestexceptions by test time is not null");
				OldTestResultList = new ArrayList<OldTestResult>();
				while (query.moveToNext()) {
					String testtime = query.getString(query
							.getColumnIndex(COLUMN_NAME_TESTTIME));
					String endtime = query.getString(query
							.getColumnIndex(COLUMN_NAME_ENDTIME));
					String isdone = query.getString(query
							.getColumnIndex(COLUMN_NAME_ISTESTDONE));
					String time_ = query.getString(query
							.getColumnIndex(COLUMN_NAME_DURATION));
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
			query = mDb.query(RESULT_TABLE_NAME, null, null, null, null,
					null, ORDER_BY);
			if (query != null && query.getCount() > 0) {
				Log.i(TAG, "AllowPermDatabaseHelper queryAllAllowIssue is not null");
				mResultList = new ArrayList<OldTestResult>();
				while (query.moveToNext()) {
					String starttime_ = query.getString(query
							.getColumnIndex(COLUMN_NAME_TESTTIME));
					String endtime = query.getString(query
							.getColumnIndex(COLUMN_NAME_ENDTIME));
					String isdone = query.getString(query
							.getColumnIndex(COLUMN_NAME_ISTESTDONE));
					String time_ = query.getString(query
							.getColumnIndex(COLUMN_NAME_DURATION));

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
