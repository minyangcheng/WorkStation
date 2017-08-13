package com.min.cs.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.min.cs.R;
import com.min.cs.adapter.PersonAdapter;
import com.min.cs.util.DataUtils;
import com.min.framework.base.BaseActivity;
import com.min.framework.widget.CenterTitleToolbar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class SnackbarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);
        mToolbar.setTitle("SnackbarActivity");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        PersonAdapter adapter=new PersonAdapter(this);
        mRv.setAdapter(adapter);
        adapter.setData(DataUtils.getPersonData(20));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_snackbar;
    }

    @OnClick(R.id.fab)
    void clickFab(){
        Snackbar.make(mFab,"this is sanckbar",Snackbar.LENGTH_SHORT)
                .show();
    }

}
