package k.kilg.speedo.domain;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import k.kilg.speedo.App;
import k.kilg.speedo.utils.SettingsUtils;

public class PrefSet {

    private Map<Prefs, String> mSettings = new HashMap<>();
    private SharedPreferences mPreference;

    public PrefSet() {

        mPreference = App.getInstance().getComponent().getSharedPref();

        for (Prefs key : Prefs.values()) {
//            mSettings.put(key, sharedPreferences.getString(key.name(), Prefs.getDefault(key)));
            mSettings.put(key, SettingsUtils.getStringPrefValue(mPreference, key, Prefs.getDefault(key)));
        }
    }

    public void update(Prefs key, String measureValue) {
        mSettings.put(key, measureValue);
    }

    public String getStringPrefValueByKey(Prefs key) {
        return mSettings.get(key);
    }

    public int getIntPrefValueByKey(Prefs key) {
        return Integer.parseInt(mSettings.get(key));
    }

    public long getLongPrefValueByKey(Prefs key) {
        return Long.parseLong(mSettings.get(key));
    }

    public Prefs.MeasureValue getMeasure() {
        return Prefs.MeasureValue.getFieldValue(mSettings.get(Prefs.MEASURE_KEY));
    }

    public Prefs.ProviderValue getProvider() {
        return Prefs.ProviderValue.getFieldValue(mSettings.get(Prefs.PROVIDER_KEY));
    }

    public Prefs.MinTimeValue getMinTime() {
        return Prefs.MinTimeValue.getFieldValue(mSettings.get(Prefs.MIN_TIME_KEY));
    }

    public Prefs.DistanceValue getDistance() {
        return Prefs.DistanceValue.getFieldValue(mSettings.get(Prefs.DISTANCE_KEY));
    }

    public boolean virifyPrefs() {
        for (Prefs key: Prefs.values()) {
            // TODO: 20.08.18 удалить на досуге лог
            Log.d("###", "verify... " + key.name() + ":" + mSettings.get(key));
            if (!mSettings.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}
