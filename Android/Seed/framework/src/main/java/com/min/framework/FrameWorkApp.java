package com.min.framework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.min.framework.util.BuildConfigUtils;
import com.min.framework.util.ImageLoaderWrap;

import timber.log.Timber;

public class FrameWorkApp extends Application {

    private static final String TAG = FrameWorkApp.class.getSimpleName();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initConfigValues();
        Utils.init(this);

        if (ConfigConstants.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        ImageLoaderWrap.init(this, ConfigConstants.DEBUG);
        registerActivityLifecycleCallbacks(mCallbacks);
    }

    public static Context getContext() {
        return context;
    }

    public void initConfigValues() {
        ConfigConstants.DEBUG = BuildConfigUtils.isDebug(this);
        ConfigConstants.API_SERVER_URL = BuildConfigUtils.getConfigStr(this, "API_SERVER_URL");
        ConfigConstants.SOURCE = BuildConfigUtils.getConfigStr(this, "SOURCE");
        ConfigConstants.APP_SECRET = BuildConfigUtils.getConfigStr(this, "APP_SECRET");
        ConfigConstants.VERSION_NAME = BuildConfigUtils.getConfigStr(this, "VERSION_NAME");
        ConfigConstants.SDK_INT = Build.VERSION.SDK_INT + "";
        ConfigConstants.FLAVOR = BuildConfigUtils.getConfigStr(this, "FLAVOR");
    }

    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d(TAG, "onActivityCreated() called with: activity = [" + activity + "], savedInstanceState = [" + savedInstanceState + "]");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], outState = [" + outState + "]");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        }
    };

}
