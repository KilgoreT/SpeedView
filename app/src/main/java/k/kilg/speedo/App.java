package k.kilg.speedo;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import k.kilg.speedo.di.component.ApplicationComponent;
import k.kilg.speedo.di.component.DaggerApplicationComponent;
import k.kilg.speedo.di.module.ApplicationModule;
import k.kilg.speedo.di.module.PresenterModule;
import k.kilg.speedo.di.module.StorageModule;
import k.kilg.speedo.utils.SettingsContract;

public class App extends Application {

    private static ApplicationComponent applicationComponent;
    private static App instance;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .presenterModule(new PresenterModule())
                .storageModule(new StorageModule())
                .build();

    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
