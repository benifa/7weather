package com.example.fabricebenimana.a7weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Prefs {

    private static final String TAG = Prefs.class.getSimpleName();
    private static Prefs instance;

    protected SharedPreferences sharedPreferences;

    private Prefs(final Context context) {
        this.sharedPreferences = context.getSharedPreferences("com.fabassignment.bbvabranches", Context.MODE_PRIVATE);
    }

    public static void init(final Context context) {
        if (context != null) {
            instance = new Prefs(context);
        }
    }

    public static Prefs getInstance() {
        if (instance == null) {
            Log.e(TAG,"Prefs instance is null, did you forget to call init?");
        }
        return instance;
    }

    public boolean getShowLocationSettings() {
        return getBooleanPref(Commons.OPEN_SETTINGS_LOCATION, false);
    }

    public void setShowLocationSettings(final boolean openSetting) {
        setBooleanPref(Commons.OPEN_SETTINGS_LOCATION, openSetting);
    }

    public void setBooleanPref(final String name, final boolean value) {
        sharedPreferences.edit().putBoolean(name, value).apply();
    }

    public boolean getBooleanPref(final String name, final boolean defaultValue) {
        return sharedPreferences.getBoolean(name, defaultValue);
    }
}
