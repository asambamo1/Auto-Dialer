package com.smartlifedigital.autodialer.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.smartlifedigital.autodialer.Adapters.CallListAdapter;
import com.smartlifedigital.autodialer.Helper.CallManagerHelper;
import com.smartlifedigital.autodialer.Models.Database;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.R;

import java.sql.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CallListActivity extends AppCompatActivity {

	private CallListAdapter mAdapter;
	private Context mContext;
	private Database dbHelper = new Database(this);
    @Bind(R.id.empty) View noCalls;
    @Bind(R.id.calls_list) ListView callsList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#263238")));


        mContext = this;
		setContentView(R.layout.activity_call_list);
        ButterKnife.bind(this);
		mAdapter = new CallListAdapter(this, dbHelper.getcalls());
        callsList.setAdapter(mAdapter);
	}


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter.getCount() != 0) {
            noCalls.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

	public static class MyObject implements Comparable<MyObject> {

		private Date dateTime;

		public Date getDateTime() {
			return dateTime;
		}

		public void setDateTime(Date datetime) {
			this.dateTime = datetime;
		}

		@Override
		public int compareTo(MyObject o) {
			return getDateTime().compareTo(o.getDateTime());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        mAdapter.setcalls(dbHelper.getcalls());
	        mAdapter.notifyDataSetChanged();
	    }
	}
	
	public void setcallEnabled(long id, boolean isEnabled) {
		CallManagerHelper.cancelcalls(this);
		
		Model model = dbHelper.getcall(id);
		model.isEnabled = isEnabled;
		dbHelper.updatecall(model);
		
		CallManagerHelper.setcalls(this);
	}

	public void startcallDetailsActivity(long id) {
		Intent intent = new Intent(this, CallDetailsActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
	}
	
	public void deletecall(long id) {
		final long callId = id;
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_delete_title)
                .content(R.string.confirm_delete_message)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Cancel calls
                        CallManagerHelper.cancelcalls(mContext);
                        //Delete call from DB by id
                        dbHelper.deletecall(callId);
                        //Refresh the list of the scheduled calls in the adapter
                        mAdapter.setcalls(dbHelper.getcalls());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Schedule the calls
                        try {
                            CallManagerHelper.setcalls(mContext);
                        } catch (NullPointerException e) {
                        }
                        if (mAdapter.getCount() == 0) {
                            noCalls.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .show();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new_call: {
                startcallDetailsActivity(-1);
                return true;
            }
            case R.id.settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
