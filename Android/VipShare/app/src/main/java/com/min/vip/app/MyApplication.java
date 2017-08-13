package com.min.vip.app;

import android.content.Context;
import android.util.Log;

import com.didi.virtualapk.PluginManager;
import com.min.framework.base.BaseApp;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class MyApplication extends BaseApp{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        long start = System.currentTimeMillis();
        PluginManager.getInstance(base).init();
        Log.d("MyTest", "use time:" + (System.currentTimeMillis() - start));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
