package com.smartlifedigital.autodialer.Models;

import android.provider.BaseColumns;

public final class DBColumns {
	
	public DBColumns() {}
	
	public static abstract class call implements BaseColumns {
		public static final String TABLE_NAME = "call";
		public static final String COLUMN_NAME_call_NAME = "name";
		public static final String COLUMN_NAME_call_PHONENUMBER = "phonenumber";
		public static final String COLUMN_NAME_call_LENGTH = "calllength";
		public static final String COLUMN_NAME_call_TIME_HOUR = "hour";
		public static final String COLUMN_NAME_call_TIME_MINUTE = "minute";
		public static final String COLUMN_NAME_call_REPEAT_DAYS = "days";
		public static final String COLUMN_NAME_call_REPEAT_WEEKLY = "weekly";
		public static final String COLUMN_NAME_call_TONE = "tone";
		public static final String COLUMN_NAME_call_ENABLED = "enabled";
	}
	
}
