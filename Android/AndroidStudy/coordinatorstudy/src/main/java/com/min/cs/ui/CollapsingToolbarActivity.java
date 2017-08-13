package com.min.cs.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.min.cs.R;
import com.min.cs.adapter.PersonAdapter;
import com.min.cs.util.DataUtils;
import com.min.framework.base.BaseActivity;
import com.min.framework.widget.CenterTitleToolbar;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class CollapsingToolbarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv)
    RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        PersonAdapter adapter=new PersonAdapter(this);
        mRv.setAdapter(adapter);
        adapter.setData(DataUtils.getPersonData(20));
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        super.initToolbar(toolbar);
        toolbar.inflateMenu(R.menu.menu_normal);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collapsing_toolbar;
    }

}
