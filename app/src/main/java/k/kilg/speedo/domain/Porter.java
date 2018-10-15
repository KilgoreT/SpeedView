package k.kilg.speedo.domain;

import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.os.Build;

import java.text.NumberFormat;

public class Porter{

    private Prefs.MeasureValue mKeyMeasure;
    private Location mLocation;

    public Porter(Location location) {
        this.mLocation = location;
        this.mKeyMeasure = Prefs.MeasureValue.KMH;
    }

    public Porter(Location location, Prefs.MeasureValue measure) {
        this.mLocation = location;
        this.mKeyMeasure = measure;
    }

    public Double getLatitude() {
        return mLocation.getLatitude();
    }

    public Double getLongitude() {
        return mLocation.getLongitude();
    }

    public String getProvider() {
        return mLocation.getProvider();
    }

    public String getExample() {
        mLocation.getTime();
        mLocation.getAccuracy();
        mLocation.getAltitude();
        mLocation.getBearing();
        mLocation.getElapsedRealtimeNanos();
        mLocation.getExtras();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLocation.getSpeedAccuracyMetersPerSecond();
            mLocation.getVerticalAccuracyMeters();
        }
        return "";
    }

    public String getSpeed() {
        String speed;
        switch (mKeyMeasure) {
            case MS:
                speed = getNumberFormat().format(mLocation.getSpeed()) + " m/s";
                break;
            default:
                speed = getNumberFormat().format(getKmHSpeed(mLocation.getSpeed())) + " km/h";
        }
        return speed;
    }

    private NumberFormat getNumberFormat() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMinimumIntegerDigits(1);
        numberFormat.setMaximumIntegerDigits(3);
        return numberFormat;
    }

    private Float getKmHSpeed(float speed) {
        return  (speed * 60 * 60)/ 1000;
    }
}
