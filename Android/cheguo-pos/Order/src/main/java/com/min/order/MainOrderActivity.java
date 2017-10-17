package com.min.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.min.core.base.BaseActivity;
import com.min.ui.widget.CenterTitleToolbar;

import butterknife.BindView;

public class MainOrderActivity extends BaseActivity {

    @BindView(R2.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R2.id.tv_extras)
    TextView mExtrasTv;
    @BindView(R2.id.tv_query)
    TextView mQueryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if(intent.getData()!=null){
            Bundle bundle = intent.getExtras();
            mExtrasTv.append("extras:\n\n");
            for (String key : bundle.keySet()) {
                mExtrasTv.append(key + "=" + bundle.get(key) + "\n\n");
            }
            mQueryTv.append("query:\n\n");
            String commpanyName = intent.getData().getQueryParameter("company_name");
            mQueryTv.append("commpanyName=" + commpanyName + "\n\n");
            String userId = intent.getData().getQueryParameter("user_id");
            mQueryTv.append("user_id=" + userId + "\n\n");
        }
    }

    private void initView() {
        mToolbar.setTitle("订单首页");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}