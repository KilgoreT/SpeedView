package k.kilg.speedo.ui.main.speed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.xml.sax.Locator;

import java.text.NumberFormat;

import javax.inject.Inject;

import k.kilg.speedo.App;
import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.model.LocatorModel;
import k.kilg.speedo.ui.base.BasePresenter;
import k.kilg.speedo.ui.base.MvpFragmentView;
import k.kilg.speedo.utils.SettingsUtils;

public class SpeedPresenter<V extends SpeedMvpView> extends BasePresenter<V>
        implements SpeedMvpPresenter<V> {


    private static int index = 0;
    private Porter mPorter;

//    @Inject
    public SpeedPresenter() {
        super();
        index++;
    }


    @Override
    public void onAttach(V mvpFragmentView) {
        super.onAttach(mvpFragmentView);
        showLog("onAttach, index = " + index);
        if (mPorter == null) {
            if (isViewAttached()) {
                getView().showLoading();
            }
        } else {
            getView().setUp(mPorter);
        }

    }

    @Override
    public void setData(Porter porter) {

        // TODO: 26.08.18 когда фрагмент исчезает?, презентер остается и вызывается этот его
        // метод. Проблема в том, что пока состояние фрагмента не сохраняется и переключение
        // с настроек вызывает loading.
        if (!isViewAttached()) return;

        mPorter = porter;

        if (isViewAttached()) {
            getView().hideLoading();
            getView().setUp(porter/*, speed*/);
        }
    }

    private String setKmHnumberFormat(float speed) {
        Float kmh = (speed * 60 * 60)/ 1000;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMinimumIntegerDigits(1);
        numberFormat.setMaximumIntegerDigits(3);
        return numberFormat.format(kmh) + " km/h";
    }

    private String setMSnumberFormat(float speed) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(1);
        numberFormat.setMinimumFractionDigits(1);
        numberFormat.setMinimumIntegerDigits(1);
        numberFormat.setMaximumIntegerDigits(2);
        return numberFormat.format(speed) + " m/s";
    }

    @Override
    public void onViewPrepared() {

    }
}
