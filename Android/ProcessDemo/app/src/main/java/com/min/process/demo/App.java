package com.min.process.demo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/12.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Timber.d("application oncreate");
    }
}
