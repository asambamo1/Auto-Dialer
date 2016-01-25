package com.smartlifedigital.autodialer.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.smartlifedigital.autodialer.Models.DBColumns.call;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "callclock.db";
	
	private static final String SQL_CREATE_call = "CREATE TABLE " + call.TABLE_NAME + " (" +
			call._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			call.COLUMN_NAME_call_NAME + " TEXT," +
			call.COLUMN_NAME_call_PHONENUMBER + " TEXT," +
			call.COLUMN_NAME_call_LENGTH + " TEXT," +
			call.COLUMN_NAME_call_TIME_HOUR + " INTEGER," +
			call.COLUMN_NAME_call_TIME_MINUTE + " INTEGER," +
			call.COLUMN_NAME_call_REPEAT_DAYS + " TEXT," +
			call.COLUMN_NAME_call_REPEAT_WEEKLY + " BOOLEAN," +
			call.COLUMN_NAME_call_TONE + " TEXT," +
			call.COLUMN_NAME_call_ENABLED + " BOOLEAN" +
	    " )";
	
	private static final String SQL_DELETE_call =
		    "DROP TABLE IF EXISTS " + call.TABLE_NAME;
    
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_call);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_call);
        onCreate(db);
	}


	
	private Model populateModel(Cursor c) {
		Model model = new Model();
		model.id = c.getLong(c.getColumnIndex(call._ID));
		model.name = c.getString(c.getColumnIndex(call.COLUMN_NAME_call_NAME));
		model.phonenumber = c.getString(c.getColumnIndex(call.COLUMN_NAME_call_PHONENUMBER));
		model.calllength = c.getString(c.getColumnIndex(call.COLUMN_NAME_call_LENGTH));
		model.timeHour = c.getInt(c.getColumnIndex(call.COLUMN_NAME_call_TIME_HOUR));
		model.timeMinute = c.getInt(c.getColumnIndex(call.COLUMN_NAME_call_TIME_MINUTE));
		model.repeatWeekly = c.getInt(c.getColumnIndex(call.COLUMN_NAME_call_REPEAT_WEEKLY)) == 0 ? false : true;
		model.callTone = c.getString(c.getColumnIndex(call.COLUMN_NAME_call_TONE)) != "" ? Uri.parse(c.getString(c.getColumnIndex(call.COLUMN_NAME_call_TONE))) : null;
		model.isEnabled = c.getInt(c.getColumnIndex(call.COLUMN_NAME_call_ENABLED)) == 0 ? false : true;
		
		String[] repeatingDays = c.getString(c.getColumnIndex(call.COLUMN_NAME_call_REPEAT_DAYS)).split(",");
		for (int i = 0; i < repeatingDays.length; ++i) {
			model.setRepeatingDay(i, repeatingDays[i].equals("false") ? false : true);
		}
		
		return model;
	}
	
	private ContentValues populateContent(Model model) {
		ContentValues values = new ContentValues();
        values.put(call.COLUMN_NAME_call_NAME, model.name);
		values.put(call.COLUMN_NAME_call_PHONENUMBER, model.phonenumber);
		values.put(call.COLUMN_NAME_call_LENGTH, model.calllength);
        values.put(call.COLUMN_NAME_call_TIME_HOUR, model.timeHour);
        values.put(call.COLUMN_NAME_call_TIME_MINUTE, model.timeMinute);
        values.put(call.COLUMN_NAME_call_REPEAT_WEEKLY, model.repeatWeekly);
        values.put(call.COLUMN_NAME_call_TONE, model.callTone != null ? model.callTone.toString() : "");
        values.put(call.COLUMN_NAME_call_ENABLED, model.isEnabled);
        
        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
        	repeatingDays += model.getRepeatingDay(i) + ",";
        }
        values.put(call.COLUMN_NAME_call_REPEAT_DAYS, repeatingDays);
        
        return values;
	}


	public long createcall(Model model) {
		ContentValues values = populateContent(model);
        return getWritableDatabase().insert(call.TABLE_NAME, null, values);
	}
	
	public long updatecall(Model model) {
		ContentValues values = populateContent(model);
        return getWritableDatabase().update(call.TABLE_NAME, values, call._ID + " = ?", new String[] { String.valueOf(model.id) });
	}
	
	public Model getcall(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
        String select = "SELECT * FROM " + call.TABLE_NAME + " WHERE " + call._ID + " = " + id;
		
		Cursor c = db.rawQuery(select, null);
		
		if (c.moveToNext()) {
			return populateModel(c);
		}
		
		return null;
	}
	
	public List<Model> getcalls() {
		SQLiteDatabase db = this.getReadableDatabase();
		
        String select = "SELECT * FROM " + call.TABLE_NAME;
		
		Cursor c = db.rawQuery(select, null);
		
		List<Model> callList = new ArrayList<Model>();
		
		while (c.moveToNext()) {
			callList.add(populateModel(c));
		}
		
		if (!callList.isEmpty()) {
			return callList;
		}
		
		return null;
	}
	
	public int deletecall(long id) {
		return getWritableDatabase().delete(call.TABLE_NAME, call._ID + " = ?", new String[] { String.valueOf(id) });
	}
}
