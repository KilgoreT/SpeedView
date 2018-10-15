package k.kilg.speedo.di.module;


import dagger.Module;
import dagger.Provides;
import k.kilg.speedo.di.PerZhopa;
import k.kilg.speedo.ui.main.MainMvpPresenter;
import k.kilg.speedo.ui.main.MainMvpView;
import k.kilg.speedo.ui.main.MainPresenter;
import k.kilg.speedo.ui.main.speed.SpeedMvpPresenter;
import k.kilg.speedo.ui.main.speed.SpeedMvpView;
import k.kilg.speedo.ui.main.speed.SpeedPresenter;

@Module
public class PresenterModule {

    @PerZhopa
    @Provides
    public SpeedMvpPresenter<SpeedMvpView> provideSpeedPresenter() {
        return new SpeedPresenter<>();
    }

    @PerZhopa
    @Provides
    public MainMvpPresenter<MainMvpView> provideMainPresenter() {
        return new MainPresenter<>();
    }
}
