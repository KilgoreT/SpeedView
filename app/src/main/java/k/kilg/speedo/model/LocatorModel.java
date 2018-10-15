package k.kilg.speedo.model;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.domain.Prefs;
import k.kilg.speedo.domain.PrefSet;
import k.kilg.speedo.utils.SettingsUtils;

public class LocatorModel extends ViewModel{

    private OnLocatorListener mListener;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mPreviousLocation;

    private PrefSet mSettings;

    private Prefs.MeasureValue mMeasure;
    private Prefs.ProviderValue mProvider;
    private Prefs.MinTimeValue mMinTime;
    private Prefs.DistanceValue  mMinDistance;

    public LocatorModel(LocationManager mLocationManager, PrefSet settings, Object object) {

        this.mLocationManager = mLocationManager;

        this.mSettings = settings;
        setSettings();

        if (object instanceof OnLocatorListener) {
            mListener = (OnLocatorListener) object;
        } else {
            throw new RuntimeException(object.toString()
                    + " must implement OnLocatorListener");
        }
    }

    public void updateSettings(PrefSet settings) {
        this.mSettings = settings;
        setSettings();
        restartLocate();
    }

    private void setSettings() {
        mMeasure = mSettings.getMeasure();
        showLog("check_prefs: mMeasure = " + mMeasure.name());
        mProvider = mSettings.getProvider();
        showLog("check_prefs: mProvider = " + mProvider.name());
        mMinTime = mSettings.getMinTime();
        showLog("check_prefs: mMinTime = " + mMinTime.name());
        mMinDistance = mSettings.getDistance();
        showLog("check_prefs: mDistanec = " + mMinDistance.name());
    }

    public void startLocate() {
        createLocationListener();
        requestLocation();
    }

    public void stopLocate() {
        mLocationManager.removeUpdates(mLocationListener);
    }

    public void restartLocate() {
        stopLocate();
        startLocate();
    }

    public void destroyLocator() {
        mListener = null;
        mLocationManager = null;
    }

    private LocationListener createLocationListener() {
        if (mLocationListener != null) return mLocationListener;
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (isBetterLocation(mPreviousLocation, location)) {
                    // TODO: 20.08.18 допилить портер до простоты
                    mListener.showLocate(new Porter(location, mMeasure));
                    mPreviousLocation = location;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                showLog("status changed");
            }

            @Override
            public void onProviderEnabled(String provider) {
                showLog("provider enabled: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                showLog("provider disabled: " + provider);
                // TODO: 18.08.18 кидать колбэк в майнпрезентер для проверки пермишинов
//                Intent gpsOptionsIntent = new Intent(
//                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                ((Activity)getView()).getBaseContext().startActivity(gpsOptionsIntent);
            }
        };
        return mLocationListener;
    }

    private boolean isBetterLocation(Location previousLocation, Location currentLocation) {

        long timeInterval = TimeUnit.MILLISECONDS.toSeconds(mMinTime.getValue()) * 2;

        if (previousLocation == null) {
            return true;
        }

        long timeDelta = currentLocation.getTime() - previousLocation.getTime();
        showLog("time Delta is: " + timeDelta);
        boolean isSignificantlyNewer = timeDelta > timeInterval;
        boolean isSignificantlyOlder = timeDelta < -timeInterval;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder ) {
            return false;
        }

        int accuracyDelta = (int) (currentLocation.getAccuracy() - previousLocation.getAccuracy());
        showLog("accuracy delta is: " + accuracyDelta);
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(currentLocation.getProvider(), previousLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }

        return false;

    }

    private boolean isSameProvider(String providerI, String providerII) {
        if (providerI == null) {
            return providerII == null;
        }
        return providerI.equals(providerII);
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        showLog("location settongs: mindist = " + mMinDistance.getValue() + ", mintime = " + mMinTime.getValue());
        switch (mProvider) {
            case GPS:
                showLog("only gps provider enabled");
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        mMinTime.getValue(), mMinDistance.getValue(), mLocationListener);
                break;
            case NETWORK:
                showLog("only network provider enabled");
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        mMinTime.getValue(), mMinDistance.getValue(), mLocationListener);
                break;
            default:
                showLog("both provider enabled");
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        mMinTime.getValue(), mMinDistance.getValue(), mLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        mMinTime.getValue(), mMinDistance.getValue(), mLocationListener);
        }
    }

    public void showLog(String message) {
        Log.d("###", "<" + getClass().getSimpleName() + ">: " + message);
    }

    public interface OnLocatorListener {
        void showLocate(Porter porter);

    }
}
