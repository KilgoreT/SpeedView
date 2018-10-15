package k.kilg.speedo.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import k.kilg.speedo.App;
import k.kilg.speedo.R;
import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.ui.base.BaseActivity;
import k.kilg.speedo.ui.main.settings.SettingsFragment;
import k.kilg.speedo.ui.main.speed.SpeedFragment;

public class MainActivity extends BaseActivity
        implements MainMvpView,
        SpeedFragment.OnFragmentInteractionListener {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    SpeedFragment mSpeedFragment;
    SettingsFragment mSettingsFragment;
    FragmentManager mFragmentManager;

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().getComponent().injectMainActivity(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
        setFragment(observeSpeedFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_bottom_speed:
//                        item.setChecked(true);
                        setFragment(observeSpeedFragment());
                        break;
                    case R.id.menu_bottom_settings:
//                        item.setChecked(true);
                        setFragment(observeSettingsFragment());
                        break;
                    default:
                            showMessage("what?????");
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_options_menu:
                break;
            default:
                break;
        }
        return true;
    }

    // todo: переименовать
    private FragmentManager observeFragmentManager() {
        if (mFragmentManager == null) createFragmentManager();
        return mFragmentManager;
    }

    //todo: переименовать тоже
    private SpeedFragment observeSpeedFragment() {
        if (mSpeedFragment == null) {
            showLog("SpeedFragment.newInstance()");
            mSpeedFragment = SpeedFragment.newInstance();
        }
        return mSpeedFragment;
    }

    // TODO: 22.07.18 переименовать тоже
    private SettingsFragment observeSettingsFragment() {
        if (mSettingsFragment == null) {
            showLog("SettingsFragment.newInstance()");
            mSettingsFragment = SettingsFragment.newInstance();
        }
        return mSettingsFragment;
    }

    private void createFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
    }

    private void setFragment(Fragment fragment) {
        observeFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment/*, fragment.getFragmentTag()*/)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        showLog("Main activity on pause");
        destroyLocation();
    }

    @Override
    public LocationManager getLocationManager() {
        return (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void createLocation() {
        showLog("createLocation()");
        mPresenter.startLocate();
    }

    @Override
    public void destroyLocation() {
        showLog("destroy location");
        mPresenter.stopLocate();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setUpFragment(Porter porter) {
        observeSpeedFragment().setData(porter);
    }
}
