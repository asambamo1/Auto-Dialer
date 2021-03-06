package com.smartlifedigital.autodialer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartlifedigital.autodialer.Activities.CallDetailsActivity;
import com.smartlifedigital.autodialer.Activities.CallListActivity;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.Preferences.AutoDialerSettings;
import com.smartlifedigital.autodialer.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CallListAdapter extends BaseAdapter implements Filterable {

	public ToggleButton btnToggle;
	private Context mContext;
	private List<Model> mCalls;
	private CallListActivity cla;
    public String mSearchText;
    List<Model> mCallFilterList;
	public ArrayList<Model> filterList;
    ValueFilter valueFilter;

	public CallListAdapter(Context context, List<Model> calls) {
		mContext = context;
		mCalls = calls;
        mCallFilterList = mCalls;
	}

	public void setCalls(List<Model> calls) {
		mCalls = calls;
	}

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

	//Filter Constraints
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                mSearchText = "";
				filterList = new ArrayList<Model>();
                for (int i = 0; i < mCallFilterList.size(); i++) {

                    Model call = new Model(mCallFilterList.get(i).getId(), mCallFilterList.get(i).getTimeHour(),
                            mCallFilterList.get(i).getTimeMinute(),mCallFilterList.get(i).getCalllength(), mCallFilterList.get(i).getRepeatingDays(),
                            mCallFilterList.get(i).getIsRepeatWeekly(),  mCallFilterList.get(i).getIsEnabled(),  mCallFilterList.get(i).getCallTone(),
                            mCallFilterList.get(i).getName(), mCallFilterList.get(i)
                            .getPhonenumber());

                    if ((mCallFilterList.get(i).getName().toUpperCase())
                            .startsWith(constraint.toString().toUpperCase())||mCallFilterList.get(i).getPhonenumber().startsWith(constraint.toString())) {
                        mSearchText = constraint.toString();
                        filterList.add(call);

                    }else{
                        // Break the prefix into "words"
                        final String[] words = mCallFilterList.get(i).getName().toUpperCase().split(" ");
                        final int wordCount = words.length;

                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(constraint.toString().toUpperCase())) {
                                mSearchText = constraint.toString();
                                filterList.add(call);
                                break;
                            }
                        }
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mCallFilterList.size();
                results.values = mCallFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                mCalls = (ArrayList<Model>) results.values;
                notifyDataSetChanged();
        }
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

	/*static class ViewHolder {
		@Bind(R.id.call_item_name)
		TextView name;
		//@Bind(R.id.job_title)
		//TextView jobTitle;

		public ViewHolder(View view) {

		}
	}*/

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		/*ViewHolder holder;
		if (view != null) {
			holder = (ViewHolder) view.getTag();
		} else {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.call_list_item, parent, false);
			ButterKnife.bind(this, view);
			holder = new ViewHolder(view);
			view.setTag(holder);
		}

		try {
			holder.name.setText("John Doe");
		}catch (Exception e){

		}*/

		//Set View
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.call_list_item, parent, false);
		}

		//loadSavedPreferences();

		//Define Model
		Model model = (Model) getItem(position);

		//Set Color as Green if day is enabled and black if day is disabled
		updateTextColor((TextView) view.findViewById(R.id.call_item_sunday), model.getRepeatingDay(Model.SUNDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_monday), model.getRepeatingDay(Model.MONDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_tuesday), model.getRepeatingDay(Model.TUESDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_wednesday), model.getRepeatingDay(Model.WEDNESDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_thursday), model.getRepeatingDay(Model.THURSDAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_friday), model.getRepeatingDay(Model.FRDIAY));
		updateTextColor((TextView) view.findViewById(R.id.call_item_saturday), model.getRepeatingDay(Model.SATURDAY));
		updateTextColor((TextView) view.findViewById(R.id.phone_call_repeat), model.repeatWeekly);

		//Get Shared Preferences for Time format and Number Visibility
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
		StringBuilder Time = new StringBuilder();
		Time.append(SP.getBoolean("time", true));
		StringBuilder Number = new StringBuilder();
		Number.append(SP.getBoolean("number", true));

		//Convert to 12 hour format
		int hour = model.timeHour;
		int minutes = model.timeMinute;

        TextView txtTime = (TextView) view.findViewById(R.id.call_item_time);
		if(Time.toString().equals("true")) {
			txtTime.setText(String.format("%02d:%02d", model.timeHour, model.timeMinute));
		}
		else if(Time.toString().equals("false")){
			boolean isPM = (hour >= 12);
			txtTime.setText(String.format("%01d:%02d %s", (hour == 12 || hour == 0) ? 12 : hour % 12, minutes, isPM ? "PM" : "AM"));
		}

		//Set name as "Unnamed Call" if user gives call no name
		TextView txtName = (TextView) view.findViewById(R.id.call_item_name);
		txtName.setText(model.name);

        if(model.name.isEmpty()){
            model.setName("Unnamed Call");
            txtName.setText(R.string.no_name);
        }

		//If user hides phone number in settings, hide phone number in list
		TextView txtNumber = (TextView) view.findViewById(R.id.number);
		txtNumber.setText(model.phonenumber);

		if(Number.toString().equals("false")){
			txtNumber.setVisibility(View.GONE);
		}
		else if(Number.toString().equals("true")){
			txtNumber.setVisibility(View.VISIBLE);
		}

		//Toggle to enable and disable the call
		btnToggle = (ToggleButton) view.findViewById(R.id.call_item_toggle);
		btnToggle.setChecked(model.isEnabled);
		btnToggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});


		btnToggle.setTag(Long.valueOf(model.id));
		btnToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               ((CallListActivity) mContext).setcallEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
			}
		});

		//Call Now Button
		Button btn = (Button) view.findViewById(R.id.button);
		final String number = model.phonenumber;
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(number)));
				mContext.startActivity(intent);
			}
		});

		//On click go to CallDetailsActivity.class
		view.setTag(Long.valueOf(model.id));
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				((CallListActivity) mContext).startcallDetailsActivity(((Long) view.getTag()).longValue());
			}
		});

		//On Long Click, Delete Call
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				((CallListActivity) mContext).deletecall(((Long) view.getTag()).longValue());
				return true;
			}
		});

        //HIGHLIGHT SEARCHED TEXT...
        String fullText = model.name;
		String fullNum= model.phonenumber;

		//Highlight the Name
        if (mSearchText != null && !mSearchText.isEmpty()) {
            int startPos = fullText.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US));
            int endPos = startPos + mSearchText.length();


            if (startPos != -1) {
                Spannable spannable = new SpannableString(fullText);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GREEN});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtName.setText(spannable);
            } else {
                txtName.setText(fullText);
            }
        } else {
            txtName.setText(fullText);
        }

		//Highlight the Phone Number
        if (mSearchText != null && !mSearchText.isEmpty()) {
            int startPos = fullNum.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US));
            int endPos = startPos + mSearchText.length();

            if (startPos != -1) {
                Spannable spannable = new SpannableString(fullNum);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GREEN});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtNumber.setText(spannable);
            } else {
                txtNumber.setText(fullNum);
            }
        } else {
            txtNumber.setText(fullNum);
        }

		return view;
	}

	private void updateTextColor(TextView view, boolean isOn) {
		if (isOn) {
			view.setTextColor(Color.GREEN);
		} else {
			view.setTextColor(Color.BLACK);
		}
	}

	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		boolean heatValue = sharedPreferences.getBoolean("Heat_Value", false);
		if(btnToggle != null) {
			if (heatValue) {
				btnToggle.setChecked(true);
			} else {
				btnToggle.setChecked(false);
			}
		}
	}

	private void savePreferences(String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
