package com.min.cs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.min.cs.event.ThemeChangeEvent;
import com.min.cs.ui.BehaviorActivity;
import com.min.cs.ui.CollapsingToolbarActivity;
import com.min.cs.ui.ScrollFlagActivity;
import com.min.cs.ui.SnackbarActivity;
import com.min.cs.ui.TabActivity;
import com.min.cs.ui.ThemeControlActivity;
import com.min.cs.util.ThemeUtils;
import com.min.framework.base.BaseActivity;
import com.min.framework.util.L;
import com.min.framework.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_snackbar)
    void clickBtnSanckBar(){
        goToDest(SnackbarActivity.class);
    }

    @OnClick(R.id.btn_scrollflag)
    void clickBtnScrollbar(){
        goToDest(ScrollFlagActivity.class);
    }

    @OnClick(R.id.btn_collapsingtoolbar)
    void clickBtnbtnCollapsingToolbar(){
        goToDest(CollapsingToolbarActivity.class);
    }

    @OnClick(R.id.btn_tab)
    void clickBtnTab(){
        goToDest(TabActivity.class);
    }

    @OnClick(R.id.btn_behavior)
    void clickBtnBehavior(){
        goToDest(BehaviorActivity.class);
    }

    @OnClick(R.id.btn_theme)
    void clickBtnThemeControl(){
        goToDest(ThemeControlActivity.class);
    }

    private void goToDest(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ThemeChangeEvent event){
        ThemeUtils.setActivityTheme(this);
        changeViewsTheme();
    }

    void changeViewsTheme(){
        getWindow().setBackgroundDrawable(ThemeUtils.getDrawable(this,android.R.attr.windowBackground));
    }

}
