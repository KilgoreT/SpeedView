package k.kilg.speedo.ui.main.speed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import k.kilg.speedo.App;
import k.kilg.speedo.R;
import k.kilg.speedo.domain.Porter;
import k.kilg.speedo.domain.Prefs;
import k.kilg.speedo.model.LocatorModel;
import k.kilg.speedo.ui.base.BaseFragment;


public class SpeedFragment extends BaseFragment implements SpeedMvpView/*,
        SharedPreferences.OnSharedPreferenceChangeListener*/ {

    private OnFragmentInteractionListener mListener;

    private static String SPEED_FRAGMENT_TAG = "SpeedFragmentTag";

    @Inject
    SpeedMvpPresenter<SpeedMvpView> mPresenter;

    @BindView(R.id.tvLatitude)
    TextView tvLatitude;

    @BindView(R.id.tvLongitude)
    TextView tvLongitude;

    @BindView(R.id.tvProvider)
    TextView tvProvider;

    @BindView(R.id.tvSpeed)
    TextView tvSpeed;

    @BindView(R.id.container_coordinate)
    LinearLayout containerCoordinate;

    public SpeedFragment() {
    }

    public static SpeedFragment newInstance() {
        SpeedFragment fragment = new SpeedFragment();
        return fragment;
    }

    @Override
    public String getFragmentTag() {
        return SPEED_FRAGMENT_TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("onCreate");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        App.getInstance().getComponent().injectSpeedFragment(this);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);
//        ActivityComponent component = getActivityComponent();
//        if (component != null) {
//            component.inject(this);
//        }
        showLog("onCreateView");

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        showLog("onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLog("onDetach");
        mListener = null;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }

    @Override
    public void setData(Porter porter) {
        mPresenter.setData(porter);
    }

    @Override
    public void setUp(Porter porter) {
        tvLatitude.setText(String.valueOf(porter.getLatitude()));
        tvLongitude.setText(String.valueOf(porter.getLongitude()));
        tvProvider.setText(porter.getProvider());
        tvSpeed.setText(porter.getSpeed());
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
