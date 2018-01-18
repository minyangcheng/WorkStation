package com.min.hybrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.min.hybrid.library.HybridManager;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long prepareTime = HybridManager.getInstance().prepareJsBundle(SplashActivity.this);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toHome();
                    }
                }, 2000 - prepareTime);
            }
        }).start();
    }

    private void toHome() {
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

}
