package k.kilg.speedo.ui.main.speed;

import android.location.Location;

import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.ui.base.MvpFragmentView;
import k.kilg.speedo.ui.base.MvpView;

public interface SpeedMvpView extends MvpFragmentView{

    void setData(Porter porter);

    void setUp(Porter porter/*, String speed*/);

}
