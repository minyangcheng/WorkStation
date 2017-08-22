package com.baronzhang.android.weather.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.baronzhang.android.library.activity.BaseActivity;
import com.baronzhang.android.library.util.ActivityUtils;
import com.baronzhang.android.library.util.DateConvertUtils;
import com.baronzhang.android.weather.R;
import com.baronzhang.android.weather.WeatherApplication;
import com.baronzhang.android.weather.activity.component.DaggerHomePageComponent;
import com.baronzhang.android.weather.activity.module.HomePageModule;
import com.baronzhang.android.weather.model.db.entities.minimalist.Weather;
import com.baronzhang.android.weather.presenter.DrawerMenuPresenter;
import com.baronzhang.android.weather.presenter.HomePagePresenter;
import com.baronzhang.android.weather.view.fragment.DrawerMenuFragment;
import com.baronzhang.android.weather.view.fragment.HomePageFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class MainActivity extends BaseActivity
        implements HomePageFragment.OnFragmentInteractionListener, DrawerMenuFragment.OnSelectCity {


    @BindView(R.id.refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //基本天气信息
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherNameTextView;
    @BindView(R.id.publish_time_text_view)
    TextView realTimeTextView;

    @Inject
    HomePagePresenter homePagePresenter;
    DrawerMenuPresenter drawerMenuPresenter;

    private String currentCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //设置 Header 为 Material风格
        ClassicsHeader header = new ClassicsHeader(this);
        header.setPrimaryColors(this.getResources().getColor(R.color.colorPrimary), Color.WHITE);
        this.smartRefreshLayout.setRefreshHeader(header);
        this.smartRefreshLayout.setOnRefreshListener(refreshLayout -> homePagePresenter.loadWeather(currentCityId, true));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawerLayout != null;
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        HomePageFragment homePageFragment = HomePageFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homePageFragment, R.id.fragment_container);

        DaggerHomePageComponent.builder()
                .applicationComponent(WeatherApplication.getInstance().getApplicationComponent())
                .homePageModule(new HomePageModule(homePageFragment))
                .build().inject(this);

        DrawerMenuFragment drawerMenuFragment = DrawerMenuFragment.newInstance(1);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), drawerMenuFragment, R.id.fragment_container_drawer_menu);

        drawerMenuPresenter = new DrawerMenuPresenter(this, drawerMenuFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_about) {
            return true;
        } else if (id == R.id.action_feedback) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updatePageTitle(Weather weather) {
        currentCityId = weather.getCityId();
        smartRefreshLayout.finishRefresh();
        toolbar.setTitle(weather.getCityName());
        collapsingToolbarLayout.setTitle(weather.getCityName());

        tempTextView.setText(weather.getWeatherLive().getTemp());
        weatherNameTextView.setText(weather.getWeatherLive().getWeather());
        realTimeTextView.setText(getString(R.string.string_publish_time) + DateConvertUtils.timeStampToDate(weather.getWeatherLive().getTime(), DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM));
    }

    @Override
    public void addOrUpdateCityListInDrawerMenu(Weather weather) {
        drawerMenuPresenter.loadSavedCities();
    }

    @Override
    public void onSelect(String cityId) {

        assert drawerLayout != null;
        drawerLayout.closeDrawer(GravityCompat.START);

        new Handler().postDelayed(() -> homePagePresenter.loadWeather(cityId, false), 250);
    }
}
