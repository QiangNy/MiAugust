package com.ludeng.july.factorytests.model.dbdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ludeng.july.factorytests.utils.DswLog;

import java.util.ArrayList;
import java.util.List;

public class DbDatabaseHelper extends SQLiteOpenHelper {
	protected SQLiteDatabase mDb;
	static String TAG ="DbDatabaseHelper";

	public DbDatabaseHelper(Context context) {
		super(context, Constant.OLDTEST_DATABASE_NAME, null,
				Constant.OLDTEST_DATABASE_VERSION);
		this.mDb = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Constant.TABLE_NAME + " (" + Constant._ID
				+ " INTEGER PRIMARY KEY,"
				+ Constant.COLUMN_NAME_STARTTESTTIME
				+ " TEXT," 
				+ Constant.COLUMN_NAME_TIME + " TEXT,"
				+ Constant.COLUMN_NAME_ISSUE + " TEXT,"
				+ Constant.COLUMN_NAME_SCENE+  " TEXT"
				+ ");");
		
		db.execSQL("CREATE TABLE " + Constant.RESULT_TABLE_NAME + " (" + Constant._ID
				+ " INTEGER PRIMARY KEY,"
				+ Constant.COLUMN_NAME_TESTTIME
				+ " TEXT," 
				+ Constant.COLUMN_NAME_ENDTIME
				+ " TEXT,"
				+ Constant.COLUMN_NAME_ISTESTDONE
				+ " BOOLEAN,"
				+ Constant.COLUMN_NAME_DURATION+  " TEXT"
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Logs that the database is being upgraded
		DswLog.i(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		// Kills the table and existing data
		db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
		
		db.execSQL("DROP TABLE IF EXISTS " + Constant.RESULT_TABLE_NAME);

		onCreate(db);
	}

	/**
	 * 插入一条新的issue数据
	 * 
	 * @param issue
	 * @return
	 */
	public long insertAllowIssue(OldTestIssue issue) {
	    DswLog.i(TAG, "oldDatabaseHelper inserttp");
	    DswLog.i(TAG,"scene="+issue.getScene());
		ContentValues cv = new ContentValues();
		cv.put(Constant.COLUMN_NAME_STARTTESTTIME, issue.getStarttime());
		cv.put(Constant.COLUMN_NAME_TIME, issue.getIssuetime());
		cv.put(Constant.COLUMN_NAME_ISSUE, issue.getPackagename()+issue.getIssue());
		cv.put(Constant.COLUMN_NAME_SCENE, issue.getScene());
		long row = mDb.insert(Constant.TABLE_NAME, null, cv);
		DswLog.i(TAG, "row=" + row);
		return row;
	}

	/**
	 * 删除特定的issue记录
	 * 
	 * @param time
	 * @param scene
	 * @return
	 */
	public boolean deleteAllowIssueByTimeAndScene(String time,
			String scene) {
	    DswLog.i(TAG, "TPDatabaseHelper deletetpException");
		String where = Constant.COLUMN_NAME_TIME + "= ?" + " AND "
				+ Constant.COLUMN_NAME_SCENE + "= ?";
		String[] whereValue = new String[] { time, scene };
		return mDb.delete(Constant.TABLE_NAME, where, whereValue) > 0;
	}

	/**
	 * 删除所有的issue记录
	 * 
	 * @return
	 */
	public boolean deleteAllOldtestIssue() {
	    DswLog.i(TAG, "AllowTPDatabaseHelper deleteAllissue");
		return mDb.delete(Constant.TABLE_NAME, null, null) >= 0;
	}

	/*
	 * 查询特定时间的Issue记录
	 */
	public List<OldTestIssue> queryOldtestBytesttime(String starttestTime) {
		DswLog.i(TAG, "query oldtestexceptions by test time");
		String where = Constant.COLUMN_NAME_STARTTESTTIME + "= ?";
		String[] whereValue = new String[] { starttestTime};
		Cursor query = null;
		List<OldTestIssue> OldTestExceptionList = null;
		try {
			query = mDb.query(Constant.TABLE_NAME, null, where, whereValue, null,
					null, Constant.ORDER_BY);

			if (query != null && query.getCount() > 0) {
				DswLog.i(TAG, "query oldtestexceptions by test time is not null");
				OldTestExceptionList = new ArrayList<OldTestIssue>();
				while (query.moveToNext()) {
					String testtime=query.getString(query.getColumnIndex(Constant.COLUMN_NAME_STARTTESTTIME));
					String time_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_TIME));
					String issue_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ISSUE));
					String scene= query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_SCENE));
					DswLog.i(TAG, "query oldtest db time_="+time_ +" issue_"+issue_+" scene="+scene +" testtime="+testtime);
					OldTestIssue mException=new OldTestIssue();
					mException.setStarttime(testtime);
					mException.setIssuetime(time_);
					mException.setIssue(issue_);
					mException.setScene(scene);
					OldTestExceptionList.add(mException);
				}
			}
		} finally {
			if (query != null) {
				query.close();
			}
		}
		return OldTestExceptionList;

	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<OldTestIssue> queryAllOldTestIssue() {
		DswLog.i(TAG, "queryAll issue");
		Cursor query = null;
		List<OldTestIssue> missues = null;
		try {
			query = mDb.query(Constant.TABLE_NAME, null, null, null, null, null,
					Constant.ORDER_BY);

			if (query != null && query.getCount() > 0) {
				DswLog.i(TAG,"AllowPermDatabaseHelper queryAllAllowIssue is not null");
				missues = new ArrayList<OldTestIssue>();
				while (query.moveToNext()) {
					String startTime=query.getString(query.
							getColumnIndex(Constant.COLUMN_NAME_STARTTESTTIME));
					String time_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_TIME));
					String issue = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_ISSUE));
					String scene_ = query.getString(query
							.getColumnIndex(Constant.COLUMN_NAME_SCENE));
					DswLog.i(TAG,"startTime="+startTime+"time_="+time_+" issue="+issue+" scene="+scene_);
					if(startTime == null || startTime.isEmpty()){
						continue;
					}
					OldTestIssue missue = new OldTestIssue();
					missue.setStarttime(startTime);
					missue.setIssuetime(time_);
					missue.setIssue(issue);
					missue.setScene(scene_);
					missues.add(missue);
				}
			}
		} finally {
			if (query != null) {
				query.close();
			}
		}

		return missues;
	}
}
