package com.smartlifedigital.autodialer.Models;

import android.net.Uri;

public class Model {

	public static final int SUNDAY = 0;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRDIAY = 5;
	public static final int SATURDAY = 6;
	
	public long id = -1;
	public int timeHour;
	public int timeMinute;
	public String calllength;
	private boolean repeatingDays[];
	public boolean repeatWeekly;
	public Uri callTone;
	public String name;
	public String phonenumber;
	public boolean isEnabled;
	
	public Model() {
		repeatingDays = new boolean[7];
	}
	
	public void setRepeatingDay(int dayOfWeek, boolean value) {
		repeatingDays[dayOfWeek] = value;
	}
	
	public boolean getRepeatingDay(int dayOfWeek) {
		return repeatingDays[dayOfWeek];
	}
	
}
