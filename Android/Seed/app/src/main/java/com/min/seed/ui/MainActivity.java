package com.min.seed.ui;

import android.content.Intent;
import android.os.Bundle;

import com.min.framework.base.BaseActivity;
import com.min.framework.widget.CenterTitleToolbar;
import com.min.seed.R;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("main");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_list_sample)
    void clickListSample() {
        startActivity(new Intent(this, RefreshLoaderActivity.class));
    }

    @OnClick(R.id.btn_timer)
    void clickTimer() {
        Timber.d("name=%s,age=%s","min",12);
        Timber.d(new RuntimeException("ji"),"name=%s,age=%s","min",12);
        Timber.tag("MyTest").d("this is mytest");
        Timber.d("this is test");
    }

}
