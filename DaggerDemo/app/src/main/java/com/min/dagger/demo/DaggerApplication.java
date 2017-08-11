package com.min.dagger.demo;

import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by minyangcheng on 2017/8/9.
 */

public class DaggerApplication extends Application {
    private ApplicationComponent mAppComponent;
    @Inject
    ApplicationBean mAppBean1;
    @Inject
    ApplicationBean mAppBean2;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppComponent == null) {
            mAppComponent = DaggerApplicationComponent.create();
        }
        mAppComponent.inject(this);
        Log.d("Dagger", "Application mAppBean1:" + mAppBean1);
        Log.d("Dagger", "Application mAppBean2:" + mAppBean2);
    }


    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
