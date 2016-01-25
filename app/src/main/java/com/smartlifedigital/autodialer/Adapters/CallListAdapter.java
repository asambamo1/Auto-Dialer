package com.smartlifedigital.autodialer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.smartlifedigital.autodialer.Activities.CallListActivity;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.Preferences.AutoDialerSettings;
import com.smartlifedigital.autodialer.R;

import java.util.List;

public class CallListAdapter extends BaseAdapter {

	private Context mContext;
	private List<Model> mCalls;

	public CallListAdapter(Context context, List<Model> calls) {
		mContext = context;
		mCalls = calls;
	}

	public void setcalls(List<Model> calls) {
		mCalls = calls;
	}

	@Override
	public int getCount() {
		if (mCalls != null) {
			return mCalls.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mCalls != null) {
			return mCalls.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (mCalls != null) {
			return mCalls.get(position).id;
		}
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
		StringBuilder Time = new StringBuilder();
		Time.append(SP.getBoolean("time", true));
		StringBuilder Number = new StringBuilder();
		Number.append(SP.getBoolean("number", true));

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.call_list_item, parent, false);
		}

		Model model = (Model) getItem(position);

		int hour = model.timeHour;
		int minutes = model.timeMinute;
		String timeSet = "";
		if (hour > 12) {
			hour -= 12;
			timeSet = "PM";
		} else if (hour == 0) {
			hour += 12;
			timeSet = "AM";
		} else if (hour == 12)
			timeSet = "PM";
		else
			timeSet = "AM";

		String min = "";
		if (minutes < 10)
			min = "0" + minutes ;
		else
			min = String.valueOf(minutes);

		// Append in StringBuilder
		String aTime = new StringBuilder().append(hour).append(':')
				.append(min).append(" ").append(timeSet).toString();

        TextView txtTime = (TextView) view.findViewById(R.id.call_item_time);
		if(Time.toString().equals("true")) {
			txtTime.setText(String.format("%02d:%02d", model.timeHour, model.timeMinute));
		}
		else if(Time.toString().equals("false")){
			txtTime.setText(aTime);
		}

		TextView txtName = (TextView) view.findViewById(R.id.call_item_name);
		txtName.setText(model.name);

        if(model.name.isEmpty()){
            //txtName.setVisibility(View.GONE);
            txtName.setText(R.string.no_name);
        }

		TextView txtNumber = (TextView) view.findViewById(R.id.number);
		txtNumber.setText(model.phonenumber);

		if(Number.toString().equals("false")){
			txtNumber.setVisibility(View.GONE);
		}
		else if(Number.toString().equals("true")){
			txtNumber.setVisibility(View.VISIBLE);
		}

        updateTextColor((TextView) view.findViewById(R.id.call_item_sunday), model.getRepeatingDay(Model.SUNDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_monday), model.getRepeatingDay(Model.MONDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_tuesday), model.getRepeatingDay(Model.TUESDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_wednesday), model.getRepeatingDay(Model.WEDNESDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_thursday), model.getRepeatingDay(Model.THURSDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_friday), model.getRepeatingDay(Model.FRDIAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_saturday), model.getRepeatingDay(Model.SATURDAY));
		updateTextColor((TextView) view.findViewById(R.id.phone_call_repeat), model.repeatWeekly);

		ToggleButton btnToggle = (ToggleButton) view.findViewById(R.id.call_item_toggle);
		btnToggle.setChecked(model.isEnabled);
		btnToggle.setTag(Long.valueOf(model.id));
		btnToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((CallListActivity) mContext).setcallEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
			}
		});

		Button btn = (Button) view.findViewById(R.id.button);
		final String number = model.phonenumber;
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(number)));
				mContext.startActivity(intent);
			}
		});

		view.setTag(Long.valueOf(model.id));
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				((CallListActivity) mContext).startcallDetailsActivity(((Long) view.getTag()).longValue());
			}
		});

		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				((CallListActivity) mContext).deletecall(((Long) view.getTag()).longValue());
				return true;
			}
		});
		
		return view;
	}


	private void updateTextColor(TextView view, boolean isOn) {
		if (isOn) {
			view.setTextColor(Color.GREEN);
		} else {
			view.setTextColor(Color.BLACK);
		}
	}
}
