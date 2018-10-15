package k.kilg.speedo.ui.base;

import android.content.Context;

public interface MvpPresenter<V extends MvpView> {

    V getView();
    //Context getContext();
    void onAttach(V mvpView);
    void onDetach();
    void showLog(String message);
}
