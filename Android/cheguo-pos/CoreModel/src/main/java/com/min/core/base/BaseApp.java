package com.min.core.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.min.common.util.BuildConfigUtils;
import com.min.common.util.Utils;
import com.min.core.Constants;
import com.min.core.util.ImageLoaderWrap;

import timber.log.Timber;

public class BaseApp extends Application {

    private static final String TAG = BaseApp.class.getSimpleName();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initConstantValues();
        Utils.init(this);

        if (Constants.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        ImageLoaderWrap.init(this, Constants.DEBUG);
        registerActivityLifecycleCallbacks(mCallbacks);
    }

    public static Context getContext() {
        return context;
    }

    public void initConstantValues() {
        Constants.DEBUG = BuildConfigUtils.isDebug(this);
        Constants.VERSION_NAME = BuildConfigUtils.getConfigStr(this, "VERSION_NAME");
        Constants.SDK_INT = Build.VERSION.SDK_INT + "";
        Constants.FLAVOR = BuildConfigUtils.getConfigStr(this, "FLAVOR");
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
