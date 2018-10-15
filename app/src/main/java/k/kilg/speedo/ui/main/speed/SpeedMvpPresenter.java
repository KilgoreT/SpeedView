package k.kilg.speedo.ui.main.speed;

import android.location.Location;

import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.ui.base.MvpPresenter;

public interface SpeedMvpPresenter<V extends SpeedMvpView> extends MvpPresenter<V> {

    void setData(Porter porter);
    void onViewPrepared();
}
