package com.min.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.min.framework.R;
import com.min.framework.widget.HudDialog;

import butterknife.ButterKnife;

/**
 * Created by minyangcheng on 2016/9/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private HudDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutId()>0){
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
    }

    protected Context getContext(){
        return this;
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void initToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mToolbar.inflateMenu(R.menu.car_type);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                UIUtils.toast(ToolbarActivity.this,item.getTitle().toString());
//                return false;
//            }
//        });
    }

    protected void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog=HudDialog.createProgressHud(this);
        }
        if(!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    protected void dismissProgressDailog(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

}
