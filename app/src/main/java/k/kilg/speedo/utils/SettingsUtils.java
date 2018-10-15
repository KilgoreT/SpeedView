package k.kilg.speedo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import k.kilg.speedo.domain.Prefs;

public class SettingsUtils {

    public static SharedPreferences getSettings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

//    public static String getPrefValue(SharedPreferences sharedPreferences, String key, String defaultValue) {
//        return sharedPreferences.getString(key, defaultValue);
//    }

    public static String getStringPrefValue(SharedPreferences sharedPreferences, Prefs key, String defaultValue) {

        return sharedPreferences.getString(key.getValue(), defaultValue);
    }

    public static int getIntPrefValue(SharedPreferences sharedPreferences, Prefs key, String defaulfValue) {
        return Integer.parseInt(sharedPreferences.getString(key.name(), defaulfValue));
    }

    public static long getLongPrefValue(SharedPreferences sharedPreferences, Prefs key, String defaulfValue) {
        return Long.parseLong(sharedPreferences.getString(key.name(), defaulfValue));
    }

}
