package k.kilg.speedo.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import k.kilg.speedo.App;
import k.kilg.speedo.di.PerZhopa;
import k.kilg.speedo.di.module.ApplicationModule;
import k.kilg.speedo.di.module.PresenterModule;
import k.kilg.speedo.di.module.StorageModule;
import k.kilg.speedo.ui.main.MainActivity;
import k.kilg.speedo.ui.main.MainPresenter;
import k.kilg.speedo.ui.main.speed.SpeedFragment;

@PerZhopa
@Component(modules = {ApplicationModule.class, PresenterModule.class, StorageModule.class})
public interface ApplicationComponent {

    SharedPreferences getSharedPref();

    void injectMainActivity(MainActivity mainActivity);
    void injectSpeedFragment(SpeedFragment fragment);

}
