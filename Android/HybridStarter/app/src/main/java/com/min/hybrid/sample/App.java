package com.min.hybrid.sample;

import android.app.Application;

import com.min.hybrid.library.HybridConfiguration;
import com.min.hybrid.library.HybridManager;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.sample.util.LifecycleCallBack;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Util.shouldInit(this)) {
            mInstance = this;
            initHybrid();
            registerLifecycle();
        }
    }

    private void initHybrid() {
        HybridConfiguration configuration = new HybridConfiguration(this)
                .setPageHostUrl("http://10.10.13.117:8080")
                .setUpdateUrl("http://10.10.13.117:8080/static/config.json")
                .setInterceptActive(true);
        HybridManager.getInstance()
                .init(configuration);
    }

    private void registerLifecycle() {
        LifecycleCallBack lifecycleManager = new LifecycleCallBack();
        lifecycleManager.register(this).setOnTaskSwitchListenner(new LifecycleCallBack
                .OnTaskSwitchListener() {

            @Override
            public void onTaskSwitchToForeground() {
                HybridManager.getInstance()
                        .checkVersion();
            }

            @Override
            public void onTaskSwitchToBackground() {
            }
        });
    }

    public static App getApplication() {
        return mInstance;
    }

}
