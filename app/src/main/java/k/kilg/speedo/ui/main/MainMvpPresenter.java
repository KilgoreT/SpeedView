package k.kilg.speedo.ui.main;

import android.location.Location;
import android.location.LocationListener;

import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void startLocate();
    void stopLocate();
    void setUp(Porter porter);

}
