package com.shrihari.updatelivelocation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getPreferences(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);
    }


    public static void deletePreferences(Context context, String key) {
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.remove(key);
        editor.apply();
    }


}
