package com.ludeng.july.factorytests.model.dbdata;

import android.provider.BaseColumns;

public class Constant  implements BaseColumns{
	public  static final String OLDTEST_DATABASE_NAME = "oldtest.db";
	public static final int OLDTEST_DATABASE_VERSION = 1 ;
	public static final String TABLE_NAME = "oldtest";
	public static final String ORDER_BY = " _id DESC";
	
	public static final String RESULT_TABLE_NAME = "oldtestresult";


	/*
	 * Column definitions
	 */
	public static final String COLUMN_NAME_STARTTESTTIME="startTestTime";
	public static final String COLUMN_NAME_TIME = "exceptiontime";

	public static final String COLUMN_NAME_ISSUE = "issue";

	public static final String COLUMN_NAME_SCENE = "scene";

	//public static final String COLUMN_NAME_SCREENSHOT = "screenshot";
	
	//public static final String COLUMN_NAME_POINT="point";
	//public static final String COLUMN_NAME_PHONEVERSION="phoneVersion";
	//public static final String COLUMN_NAME_SCENES="testScene";
	
	public static final String COLUMN_NAME_TESTTIME="testtime";
	public static final String COLUMN_NAME_ENDTIME="endtime";
	public static final String COLUMN_NAME_ISTESTDONE="istestdone";
	public static final String COLUMN_NAME_DURATION="durationtime";
}
