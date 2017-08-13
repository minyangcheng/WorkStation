package com.min.cs.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.min.cs.R;
import com.min.cs.event.ThemeChangeEvent;
import com.min.cs.util.ThemeUtils;
import com.min.framework.base.BaseActivity;
import com.min.framework.util.SPUtils;
import com.min.framework.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class ThemeControlActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @Bind(R.id.btn_change_theme)
    Button mChangeBtn;
    @Bind(R.id.tv_0)
    TextView mTv_0;
    @Bind(R.id.tv_1)
    TextView mTv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme_control;
    }

    @OnClick(R.id.btn_change_theme)
    void clickChangeTheme(){
        ThemeUtils.changeThemeFlag();
        ThemeUtils.setActivityTheme(this);
        changeViewsTheme();

        EventBus.getDefault().post(new ThemeChangeEvent());
    }

    void changeViewsTheme(){
        getWindow().setBackgroundDrawable(ThemeUtils.getDrawable(this,android.R.attr.windowBackground));
        mTv_0.setText(ThemeUtils.getString(this, R.attr.themeName));
        mTv_0.setTextColor(ThemeUtils.getColor(this, R.attr.themeTextColor));
        mTv_0.setTextSize(TypedValue.COMPLEX_UNIT_PX, ThemeUtils.getDimensionPixelSize(this, R.attr.themeTextSize));
        mTv_1.setTextColor(ThemeUtils.getColor(this, R.attr.themeTextColor));
        mTv_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, ThemeUtils.getDimensionPixelSize(this, R.attr.themeTextSize));
        mChangeBtn.setBackgroundDrawable(ThemeUtils.getDrawable(this,R.attr.btnBg));
    }

}
