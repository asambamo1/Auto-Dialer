package com.smartlifedigital.autodialer.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smartlifedigital.autodialer.Adapters.SettingsAdapter;
import com.smartlifedigital.autodialer.R;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class AboutUsActivity extends AppCompatActivity{
    public static final String SUPPORT_EMAIL = "aravind.arasam@gmail.com";
    public static final String Web_URL = "http://www.smartlifedigital.com/auto_dialer";

    @Bind(R.id.coordinator_layout) CoordinatorLayout parent;
    @Bind(R.id.settings_options) ListView settingsOptions;

    @BindString(R.string.play_store_error) String playStoreError;
    @BindString(R.string.feedback_subject) String feedbackSubject;
    @BindString(R.string.send_email) String sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#263238")));

        settingsOptions.setAdapter(new SettingsAdapter(this));
    }

    @OnItemClick(R.id.settings_options)
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(this, AboutActivity.class);
                break;
            case 1:
                String uriText = "mailto:" + SUPPORT_EMAIL + "?subject=" + Uri.encode(feedbackSubject);
                Uri mailUri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(mailUri);
                startActivity(Intent.createChooser(sendIntent, sendEmail));
                return;
            case 2:
                //Uri uri =  Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.smartlifedigital.autodialer");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                if (!(getPackageManager().queryIntentActivities(intent, 0).size() > 0))
                {
                    Snackbar.make(parent, playStoreError, Snackbar.LENGTH_LONG).show();
                    return;
                }
                break;
            case 3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Web_URL));
                startActivity(intent);
                break;
            /*case 3:
                intent = new Intent(this, AboutMeActivity.class);
                break;

            case 5:
                intent = new Intent(this, AboutMeActivity.class);
                break;
            case 6:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(REPO_URL));
                break;*/
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blank_menu, menu);
        return true;
    }

    @Override
         public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
