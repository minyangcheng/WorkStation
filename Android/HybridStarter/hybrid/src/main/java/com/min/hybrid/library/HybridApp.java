package com.min.hybrid.library;

import android.app.Application;

import com.min.hybrid.library.asset.AssetCheckManager;
import com.min.hybrid.library.util.LifecycleCallBack;
import com.min.hybrid.library.util.Util;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class HybridApp extends Application {

    private static HybridApp mInstance;
    private AssetCheckManager mVersionChecker;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Util.shouldInit(this)) {
            mInstance = this;
            mVersionChecker = new AssetCheckManager(this);
            registerLifecycle();
        }
    }


    private void registerLifecycle() {
        LifecycleCallBack lifecycleManager = new LifecycleCallBack();
        lifecycleManager.register(this).setOnTaskSwitchListenner(new LifecycleCallBack
                .OnTaskSwitchListener() {

            @Override
            public void onTaskSwitchToForeground() {
                if (mVersionChecker != null) {
//                    mVersionChecker.checkVersion();
                }
            }

            @Override
            public void onTaskSwitchToBackground() {
            }
        });
    }

    public static HybridApp getApplication() {
        return mInstance;
    }

}
