package k.kilg.speedo.ui.main;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.ui.base.MvpView;

public interface MainMvpView extends MvpView{

//    SharedPreferences getSettingsPreferences();

    LocationManager getLocationManager();

    void createLocation();
    void destroyLocation();
    void setUpFragment(Porter porter);

}
