package com.min.aop_demo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
