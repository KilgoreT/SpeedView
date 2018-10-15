package k.kilg.speedo.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;

import javax.inject.Inject;

import k.kilg.speedo.App;
import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.domain.PrefSet;
import k.kilg.speedo.domain.Prefs;
import k.kilg.speedo.model.LocatorModel;
import k.kilg.speedo.ui.base.BasePresenter;
import k.kilg.speedo.utils.SettingsUtils;

import static android.content.Context.LOCATION_SERVICE;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V>,
        LocatorModel.OnLocatorListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static int index = 0;

    private LocatorModel mModel;
    private PrefSet mSettings;

    private SharedPreferences mPreferences;

    private LocationManager mLocationManager;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Inject
    public MainPresenter() {
        index++;
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        showLog("onAttach index = " + index);

        mSettings = new PrefSet();

        mPreferences = App.getInstance().getComponent().getSharedPref();
        mPreferences.registerOnSharedPreferenceChangeListener(this);

        if (verifyPermission(LOCATION_PERMS)) {
            mModel = createModel(createLocationManager());
            startLocate();
        } else {
            // TODO: 15.08.18 переделать на error
            getView().onError("something error!");
        }
    }

    @Override
    public void startLocate() {
        showLog("startLocate()");
        mModel.startLocate();
    }

    @Override
    public void stopLocate() {
        mModel.stopLocate();
    }

    private LocationManager createLocationManager() {
        showLog("create LocationManager");
        if (mLocationManager != null) return mLocationManager;
        mLocationManager = getView().getLocationManager();
        return mLocationManager;
    }

    private LocatorModel createModel(LocationManager locationManager) {
        if (mModel != null) return mModel;
        mModel = new LocatorModel(locationManager, mSettings, this);
        return mModel;
    }


    private boolean verifyPermission(String[] permissions) {
        for (String permission: permissions) {
            if (ContextCompat.checkSelfPermission(((Activity)getView()).getBaseContext(), permission) == PackageManager.PERMISSION_DENIED) {
                // TODO: 15.08.18 непонятные пока дейвствия после запроса пермишинов
                askPermission();
                return false;
            }
        }
        return true;
    }

    private boolean askPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //todo: избавиться от каста
                // TODO: 07.08.18 забодяжить внятный реквест код
                ((Activity)getView()).requestPermissions(LOCATION_PERMS, 1111);
            return askPermission();
        }
        return false;
    }

    @Override
    public void setUp(Porter porter) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPreferences.unregisterOnSharedPreferenceChangeListener(this);
        stopLocate();
        mModel.destroyLocator();
        mModel = null;
    }

    // TODO: 26.08.18 зачем и showLocate и setUp
    @Override
    public void showLocate(Porter porter) {
        if (isViewAttached()) {
            getView().setUpFragment(porter);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String value;
        Prefs prefKey = Prefs.getFieldValue(key);
        /**
         * Prefs.getFieldValue() может вернуть null
         */
        if (prefKey == null) return;
        switch (prefKey) {
            case MEASURE_KEY:
                value = SettingsUtils.getStringPrefValue(sharedPreferences, prefKey, Prefs.getDefault(prefKey));
                updateSettings(prefKey, value);
                showLog("MeasureValue changed: " + value);
                break;
            case PROVIDER_KEY:
                value = SettingsUtils.getStringPrefValue(sharedPreferences, prefKey, Prefs.getDefault(prefKey));
                updateSettings(prefKey, value);
                showLog("ProviderValue change: " + value);
                break;
            case MIN_TIME_KEY:
                value = SettingsUtils.getStringPrefValue(sharedPreferences, prefKey, Prefs.getDefault(prefKey));
                updateSettings(prefKey, value);
                showLog("Minimum time interval change: " + value);
                break;
            case DISTANCE_KEY:
                value = SettingsUtils.getStringPrefValue(sharedPreferences, prefKey, Prefs.getDefault(prefKey));
                updateSettings(prefKey, value);
                showLog("Minimum distance change: " + value);
                break;
            default:
                showLog("Unknown key: " + key);
        }
    }

    private void updateSettings(Prefs key, String value) {
        mSettings.update(key, value);
        mSettings.virifyPrefs();
        mModel.updateSettings(mSettings);
    }
}
