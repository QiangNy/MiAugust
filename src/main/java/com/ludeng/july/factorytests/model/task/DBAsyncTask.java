package com.ludeng.july.factorytests.model.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ludeng.july.factorytests.Utils.DswLog;
import com.ludeng.july.factorytests.Utils.Singleton;
import com.ludeng.july.factorytests.model.dbdata.DbDatabaseManager;
import com.ludeng.july.factorytests.model.dbdata.DbResultDatabaseManager;

import static android.support.constraint.Constraints.TAG;

public class DBAsyncTask extends BaseAsyncTask {



	public static final Uri ROSTER_CONTENT_URI = Uri.parse("content://com.cyee.settings.RosterProvider/rosters");

	public DBAsyncTask() {
		super();
	}

	@Override
	protected Object doInBackground(Object[] objects) {
		DbDatabaseManager.initDatabase(Singleton.getInstance().getmContext());
		DbResultDatabaseManager.initDatabase(Singleton.getInstance().getmContext());
		// addWhiteName
		if (!isInWhiteName(Singleton.getInstance().getmContext())) {

			addWhiteName(Singleton.getInstance().getmContext());
		}
		// addToSoftWareManagerAutostart
		if (!isInSoftWareManagerAutostart(Singleton.getInstance().getmContext())) {
			addToSoftWareManagerAutostart(Singleton.getInstance().getmContext());
		}

		return null;
	}


	public boolean isInWhiteName(Context mContext) {
		Cursor cursor = null;
		try {
			cursor = mContext.getContentResolver().query(ROSTER_CONTENT_URI,
					new String[] { "usertype", "status" },
					"packagename" + "='" + mContext.getPackageName() + "'",
					null, null);
			while (cursor.moveToNext()) {
				int usertypeColumn = cursor.getColumnIndex("usertype");
				String usertypeString = cursor.getString(usertypeColumn);
				String statusString = cursor.getString(cursor
						.getColumnIndex("status"));
				if (usertypeString.equals("oneclean")
						&& statusString.equals("2")) {
					return true;
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return false;
	}

	public void addWhiteName(Context mContext) {
		ContentValues cv = new ContentValues();
		cv.put("packagename", mContext.getPackageName());
		cv.put("usertype", "oneclean");
		cv.put("status", "2");
		mContext.getContentResolver().insert(ROSTER_CONTENT_URI, cv);
		DswLog.i(TAG, "addWhiteName");
	}

	public static void addToSoftWareManagerAutostart(Context mContext) {
		ContentValues values = new ContentValues();
		values.put("usertype", "allowboot");
		values.put("packagename", mContext.getPackageName());
		values.put("status", "1");
		mContext.getContentResolver().insert(ROSTER_CONTENT_URI, values);
		DswLog.i(TAG, "addToSoftWareManagerAutostart");
	}

	public boolean isInSoftWareManagerAutostart(Context mContext) {
		Cursor cursor = null;
		try {
			cursor = mContext.getContentResolver().query(ROSTER_CONTENT_URI,
					new String[] { "usertype", "status" },
					"packagename" + "='" + mContext.getPackageName() + "'",
					null, null);
			while (cursor.moveToNext()) {
				int usertypeColumn = cursor.getColumnIndex("usertype");
				String usertypeString = cursor.getString(usertypeColumn);
				String statusString = cursor.getString(cursor
						.getColumnIndex("status"));
				if (usertypeString.equals("allowboot")
						&& statusString.equals("1")) {
					return true;
				}
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return false;
	}
}
