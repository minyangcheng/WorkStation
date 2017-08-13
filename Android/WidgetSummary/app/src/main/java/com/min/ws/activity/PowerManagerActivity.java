package com.min.ws.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.min.ws.R;
import com.min.ws.util.PowerUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PowerManagerActivity extends AppCompatActivity {

    private PowerUtil mPowerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_manager);
        ButterKnife.bind(this);

        mPowerUtil=new PowerUtil(this);
    }

    @OnClick(R.id.btn_start_popwer)
    void clickBtnStartPower(){
        mPowerUtil.handleWakeLock(true);
    }

    @OnClick(R.id.btn_stop_popwer)
    void clickBtnStopPower(){
        mPowerUtil.handleWakeLock(false);
    }


}
