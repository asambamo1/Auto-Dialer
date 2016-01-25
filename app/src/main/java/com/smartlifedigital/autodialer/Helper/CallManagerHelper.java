package com.smartlifedigital.autodialer.Helper;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smartlifedigital.autodialer.Models.Database;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.Service.CallSchedulerService;

import java.util.Calendar;
import java.util.List;

public class CallManagerHelper extends BroadcastReceiver {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PHONENUMBER = "phonenumber";
	public static final String CALLLENGTH = "calllength";
	public static final String TIME_HOUR = "timeHour";
	public static final String TIME_MINUTE = "timeMinute";
	public static final String TONE = "callTone";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			setcalls(context);
		}catch (NullPointerException e){

		}
	}
	
	public static void setcalls(Context context) {
		cancelcalls(context);
		
		Database dbHelper = new Database(context);

		List<Model> calls =  dbHelper.getcalls();

		for (Model call : calls) {
			if (call.isEnabled) {

				PendingIntent pIntent = createPendingIntent(context, call);

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, call.timeHour);
				calendar.set(Calendar.MINUTE, call.timeMinute);
				calendar.set(Calendar.SECOND, 00);

				//Find next time to set
				final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
				boolean callset = false;

				//First check if it's later in the week
				for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
					if (call.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
							!(dayOfWeek == nowDay && call.timeHour < nowHour) &&
							!(dayOfWeek == nowDay && call.timeHour == nowHour && call.timeMinute <= nowMinute)) {
						calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

						setcall(context, calendar, pIntent);
						callset = true;
						break;
					}
				}
				
				//Else check if it's earlier in the week
				if (!callset) {
					for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
						if (call.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay && call.repeatWeekly) {
							calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
							calendar.add(Calendar.WEEK_OF_YEAR, 1);
							
							setcall(context, calendar, pIntent);
							callset = true;
							break;
						}
					}
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	private static void setcall(Context context, Calendar calendar, PendingIntent pIntent) {
		AlarmManager callManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			callManager.setExact(callManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		} else {
			callManager.set(callManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		}
	}
	
	public static void cancelcalls(Context context) {
		Database dbHelper = new Database(context);
		
		List<Model> calls =  dbHelper.getcalls();
		
 		if (calls != null) {
			for (Model call : calls) {
				if (call.isEnabled) {
					PendingIntent pIntent = createPendingIntent(context, call);
	
					AlarmManager callManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
					callManager.cancel(pIntent);
				}
			}
 		}
	}

	private static PendingIntent createPendingIntent(Context context, Model model) {
		Intent intent = new Intent(context, CallSchedulerService.class);
		intent.putExtra(ID, model.id);
		intent.putExtra(NAME, model.name);
		intent.putExtra(PHONENUMBER, model.phonenumber);
        intent.putExtra(CALLLENGTH, model.calllength);
		intent.putExtra(TIME_HOUR, model.timeHour);
		intent.putExtra(TIME_MINUTE, model.timeMinute);
		intent.putExtra(TONE, model.callTone.toString());
		
		return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
