package com.smartlifedigital.autodialer.Preferences;

import android.content.SharedPreferences;

/**
 * Created by Aravind on 1/20/2016.
 */
public class AutoDialerSettings {

    SharedPreferences mSettings;

    public AutoDialerSettings(SharedPreferences settings) {
        mSettings = settings;
    }

    public boolean isClock24() {
        StringBuilder builder = new StringBuilder();
        builder.append(mSettings.getBoolean("time", true));
        return builder.toString().equals("true");
    }

}

