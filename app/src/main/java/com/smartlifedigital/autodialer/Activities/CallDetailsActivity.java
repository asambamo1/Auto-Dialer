package com.smartlifedigital.autodialer.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rey.material.widget.Switch;
import com.smartlifedigital.autodialer.Helper.CallManagerHelper;
import com.smartlifedigital.autodialer.Models.Database;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallDetailsActivity extends AppCompatActivity {
	private Database dbHelper = new Database(this);
	
	private Model callDetails;
	
	private TimePicker timePicker;
	@Bind(R.id.call_details_name) EditText edtName;
	@Bind(R.id.autodial_number) EditText edtNumber;
	@Bind(R.id.callTimer) EditText edtTimer;
	@Bind(R.id.call_details_repeat_weekly) Switch chkWeekly;
	@Bind(R.id.call_details_repeat_sunday) Switch chkSunday;
	@Bind(R.id.call_details_repeat_monday) Switch chkMonday;
	@Bind(R.id.call_details_repeat_tuesday) Switch chkTuesday;
	@Bind(R.id.call_details_repeat_wednesday) Switch chkWednesday;
	@Bind(R.id.call_details_repeat_thursday) Switch chkThursday;
	@Bind(R.id.call_details_repeat_friday) Switch chkFriday;
	@Bind(R.id.call_details_repeat_saturday) Switch chkSaturday;
	@Bind(R.id.call_label_tone_selection) TextView txtToneSelection;
    @Bind(R.id.textView2) TextView t2;
    @Bind(R.id.coordinator_layout) CoordinatorLayout snackbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

		getSupportActionBar().setTitle("Schedule a Call");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		android.support.v7.app.ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#263238")));

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        StringBuilder builder = new StringBuilder();
        builder.append(SP.getBoolean("time", true));


		timePicker = (TimePicker) findViewById(R.id.call_details_time_picker);
        if(builder.toString().equals("true")) {
            timePicker.setIs24HourView(true);
        }

		long id = getIntent().getExtras().getLong("id");
		
		if (id == -1) {
			callDetails = new Model();
		} else {
			callDetails = dbHelper.getcall(id);
			
			timePicker.setCurrentMinute(callDetails.timeMinute);
			timePicker.setCurrentHour(callDetails.timeHour);
			
			edtName.setText(callDetails.name);
			edtNumber.setText(callDetails.phonenumber);
            try {
                edtTimer.setText(callDetails.calllength);
            }catch (Exception e){

            }

			chkWeekly.setChecked(callDetails.repeatWeekly);
			chkSunday.setChecked(callDetails.getRepeatingDay(Model.SUNDAY));
			chkMonday.setChecked(callDetails.getRepeatingDay(Model.MONDAY));
			chkTuesday.setChecked(callDetails.getRepeatingDay(Model.TUESDAY));
			chkWednesday.setChecked(callDetails.getRepeatingDay(Model.WEDNESDAY));
			chkThursday.setChecked(callDetails.getRepeatingDay(Model.THURSDAY));
			chkFriday.setChecked(callDetails.getRepeatingDay(Model.FRDIAY));
			chkSaturday.setChecked(callDetails.getRepeatingDay(Model.SATURDAY));

			txtToneSelection.setText(RingtoneManager.getRingtone(this, callDetails.callTone).getTitle(this));
		}

		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.call_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				startActivityForResult(intent , 1);
			}
		});

		Button contacts = (Button) findViewById(R.id.contacts);
		contacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
				startActivityForResult(pickContactIntent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();

			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver().query(uri, new String[]{
									ContactsContract.CommonDataKinds.Phone.NUMBER,
									ContactsContract.CommonDataKinds.Phone.TYPE },
							null, null, null);

					if (c != null && c.moveToFirst()) {
						String number = c.getString(0);
						int type = c.getInt(1);
						showSelectedNumber(type, number);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        switch (requestCode) {
		        case 1: {
		        	callDetails.callTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		        	txtToneSelection.setText(RingtoneManager.getRingtone(this, callDetails.callTone).getTitle(this));
		            break;
		        }
				default: {
		            break;
		        }
	        }
	    }
	}

	public void showSelectedNumber(int type, String number) {
			edtNumber.setText(number);
	}

    @OnClick(R.id.save_call)
    public void saveCall(View button) {
        t2.setText(edtNumber.getText().toString());
        if (t2 == null || t2.getText().toString().length() < 1) {
            Snackbar.make(snackbar, "Please enter a phone number first!", Snackbar.LENGTH_LONG).show();
        }
        else if (t2.getText().toString().trim().startsWith("911")){
            Snackbar.make(snackbar, "911 calls are not allowed!", Snackbar.LENGTH_LONG).show();
        }
		//else if (callDetails == callDetails)
        else {
            updateModelFromLayout();
            CallManagerHelper.cancelcalls(this);
            if (callDetails.id < 0) {
                dbHelper.createcall(callDetails);
            } else {
                dbHelper.updatecall(callDetails);
            }
            CallManagerHelper.setcalls(this);
            setResult(RESULT_OK);
            finish();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_set_calls, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: {
				finish();
				break;
			}
			case R.id.action_save_call_details: {
				t2.setText(edtNumber.getText().toString());
				if (t2 == null || t2.getText().toString().length() < 1) {
					View view = findViewById(android.R.id.content);
					Snackbar.make(snackbar, "Please enter a phone number first!", Snackbar.LENGTH_LONG).show();

				} else if (t2.getText().toString().startsWith("911")){
					View view = findViewById(android.R.id.content);
					Snackbar.make(view, "911 Calls are not allowed!", Snackbar.LENGTH_LONG).show();
				}

				else if(t2.getText().toString().contains(" 911")){
					View view = findViewById(android.R.id.content);
					Snackbar.make(view, "911 Calls are not allowed!", Snackbar.LENGTH_LONG).show();
				}

				/*else if(callDetails.isEnabled){

					View view = findViewById(android.R.id.content);
					Snackbar.make(view, "Duplicate Call!", Snackbar.LENGTH_LONG).show();
				}*/

				else{

					updateModelFromLayout();

					CallManagerHelper.cancelcalls(this);


					if (callDetails.id < 0) {
						dbHelper.createcall(callDetails);
					} else {
						dbHelper.updatecall(callDetails);
					}


					CallManagerHelper.setcalls(this);

					setResult(RESULT_OK);
					finish();

				}


			}

		}



		return super.onOptionsItemSelected(item);
	}
	
	private void updateModelFromLayout() {		
		callDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		callDetails.timeHour = timePicker.getCurrentHour().intValue();
		callDetails.name = edtName.getText().toString();
		callDetails.phonenumber = edtNumber.getText().toString();
		callDetails.calllength = edtTimer.getText().toString();
		callDetails.repeatWeekly = chkWeekly.isChecked();	
		callDetails.setRepeatingDay(Model.SUNDAY, chkSunday.isChecked());
		callDetails.setRepeatingDay(Model.MONDAY, chkMonday.isChecked());
		callDetails.setRepeatingDay(Model.TUESDAY, chkTuesday.isChecked());
		callDetails.setRepeatingDay(Model.WEDNESDAY, chkWednesday.isChecked());
		callDetails.setRepeatingDay(Model.THURSDAY, chkThursday.isChecked());
		callDetails.setRepeatingDay(Model.FRDIAY, chkFriday.isChecked());
		callDetails.setRepeatingDay(Model.SATURDAY, chkSaturday.isChecked());
		callDetails.isEnabled = true;
	}
}
