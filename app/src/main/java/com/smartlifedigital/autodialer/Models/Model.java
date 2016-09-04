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
    public boolean repeatingDays[];
    public boolean repeatWeekly;
    public Uri callTone;
    public String name;
    public String phonenumber;
    public boolean isEnabled;

    public Model(long id, int timeHour, int timeMinute, String calllength,
                 boolean repeatingDays[], boolean repeatWeekly, boolean isEnabled,
                 Uri callTone, String name, String phonenumber) {

        this.id = id;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.calllength = calllength;
        this.repeatingDays = repeatingDays;
        this.repeatWeekly = repeatWeekly;
        this.callTone = callTone;
        this.name = name;
        this.phonenumber = phonenumber;
        this.isEnabled = isEnabled;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setCalllength(String calllength) {
        this.calllength = calllength;
    }

    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public void setRepeatingDays(boolean[] repeatingDays) {
        this.repeatingDays = repeatingDays;
    }

    public void setCallTone(Uri callTone) {
        this.callTone = callTone;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public static int getSUNDAY() {

        return SUNDAY;
    }

    public static int getMONDAY() {
        return MONDAY;
    }

    public static int getTUESDAY() {
        return TUESDAY;
    }

    public static int getTHURSDAY() {
        return THURSDAY;
    }

    public static int getWEDNESDAY() {
        return WEDNESDAY;
    }

    public static int getFRDIAY() {
        return FRDIAY;
    }

    public static int getSATURDAY() {
        return SATURDAY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCalllength() {
        return calllength;
    }

    public boolean[] getRepeatingDays() {
        return repeatingDays;
    }

    public boolean getIsRepeatWeekly() {
        return repeatWeekly;
    }

    public Uri getCallTone() {
        return callTone;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber() {
        this.phonenumber = phonenumber;
    }

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
