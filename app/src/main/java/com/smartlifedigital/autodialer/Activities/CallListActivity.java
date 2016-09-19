package com.smartlifedigital.autodialer.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.smartlifedigital.autodialer.Adapters.CallListAdapter;
import com.smartlifedigital.autodialer.Helper.CallManagerHelper;
import com.smartlifedigital.autodialer.Models.Database;
import com.smartlifedigital.autodialer.Models.Model;
import com.smartlifedigital.autodialer.R;

import java.sql.Date;
import java.text.Normalizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CallListActivity extends AppCompatActivity {

    private CallListAdapter mAdapter;
    private Context mContext;
    private Database dbHelper = new Database(this);
    @Bind(R.id.empty)
    View noCalls;
    @Bind(R.id.calls_list)
    ListView callsList;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.search_badge)
    FrameLayout searchBar;
    @Bind(R.id.clear_search)
    com.joanzapata.iconify.widget.IconTextView clear;
    @Bind(R.id.no_call_found)
    TextView noCallFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        mContext = this;
        setContentView(R.layout.activity_call_list);
        ButterKnife.bind(this);
        mAdapter = new CallListAdapter(this, dbHelper.getcalls());
        callsList.setAdapter(mAdapter);
        callsList.setTextFilterEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        callsList.setAdapter(mAdapter);
        if (mAdapter.getCount() != 0) {
            noCalls.setVisibility(View.INVISIBLE);
            searchBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setCalls(dbHelper.getcalls());
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnTextChanged(value = R.id.search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(CharSequence s) {
        mAdapter.getFilter().filter(s);
        if(mAdapter.filterList != null && mAdapter.filterList.isEmpty()){
            noCallFound.setVisibility(View.VISIBLE);
            noCallFound.setText("No Calls Found Matching " + "' " + s.toString()  + " '");
        }
        if(mAdapter.filterList != null && !(mAdapter.filterList.isEmpty())){
            noCallFound.setVisibility(View.INVISIBLE);
        }
        if(s.toString().isEmpty()){
            noCallFound.setVisibility(View.INVISIBLE);
            if(mAdapter.mSearchText != null){
                mAdapter.mSearchText = "";
            }
            mAdapter.notifyDataSetChanged();
        }
        if ((search.getText().toString().equals(""))){
            clear.setVisibility(View.GONE);
        }
        else{
            clear.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.clear_search)
    public void clearSearch(View view){
        search.setText("");
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

    public void setcallEnabled(long id, boolean isEnabled) {
        CallManagerHelper.cancelcalls(this);

        Model model = dbHelper.getcall(id);
        try {
            model.isEnabled = isEnabled;
        } catch (NullPointerException e) {
            System.out.print("Caught Exception!");
        }
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
                        mAdapter.setCalls(dbHelper.getcalls());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Schedule the calls
                        try {
                            CallManagerHelper.setcalls(mContext);
                            refresh();
                        } catch (NullPointerException e) {
                            System.out.print("Caught Exception!");
                        }
                        if (mAdapter.getCount() == 0) {
                            noCalls.setVisibility(View.VISIBLE);
                            searchBar.setVisibility(View.GONE);
                        }
                    }
                })
                .show();
    }

    public void refresh(){
        if (!(search.getText().toString().isEmpty())) {
            search.setText("");
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
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
