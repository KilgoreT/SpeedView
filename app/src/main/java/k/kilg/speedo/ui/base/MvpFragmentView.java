package k.kilg.speedo.ui.base;

import android.app.Activity;
import android.content.Context;

public interface MvpFragmentView extends MvpView {
    Activity getFragmentActivity();
    Context getFragmentContext();
}
