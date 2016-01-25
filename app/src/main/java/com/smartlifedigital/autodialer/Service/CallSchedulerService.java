package com.smartlifedigital.autodialer.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.smartlifedigital.autodialer.Activities.CallReminderScreenActivity;
import com.smartlifedigital.autodialer.Helper.CallManagerHelper;


public class CallSchedulerService extends Service {



	public static String TAG = CallSchedulerService.class.getSimpleName();



	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {


		try {
			Intent callIntent = new Intent(getBaseContext(), CallReminderScreenActivity.class);
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			callIntent.putExtras(intent);
			getApplication().startActivity(callIntent);

			CallManagerHelper.setcalls(this);



		}
		catch (NullPointerException e){

		}

		return super.onStartCommand(intent, flags, startId);
	}
	
}