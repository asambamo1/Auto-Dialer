package com.smartlifedigital.autodialer.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.smartlifedigital.autodialer.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutMeActivity extends AppCompatActivity {
    public static final String LINKEDIN_URL = "https://www.linkedin.com/pub/aravind-sambamoorthy/a6/280/12a";
    public static final String GITHUB_URL = "https://github.com/asambamo1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.linkedin)
    public void openLinkedIn(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINKEDIN_URL));
        startActivity(intent);
    }

    @OnClick(R.id.github)
    public void openGitHub(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blank_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
