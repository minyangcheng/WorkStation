package com.min.framework;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.blankj.utilcode.util.Utils;
import com.min.framework.util.BuildConfigUtils;
import com.min.framework.util.ImageLoaderWrap;

import timber.log.Timber;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initConfigValues();
        ImageLoaderWrap.init(this,ConfigConstants.DEBUG);
        if (ConfigConstants.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        Utils.init(this);
    }

    public static Context getContext(){
        return context;
    }

    public void initConfigValues() {
        ConfigConstants.DEBUG= BuildConfigUtils.isDebug(this);
        ConfigConstants.API_SERVER_URL = BuildConfigUtils.getConfigStr(this,"API_SERVER_URL");
        ConfigConstants.SOURCE = BuildConfigUtils.getConfigStr(this,"SOURCE");
        ConfigConstants.APP_SECRET = BuildConfigUtils.getConfigStr(this,"APP_SECRET");
        ConfigConstants.VERSION_NAME = BuildConfigUtils.getConfigStr(this,"VERSION_NAME");
        ConfigConstants.SDK_INT = Build.VERSION.SDK_INT+"";
        ConfigConstants.FLAVOR = BuildConfigUtils.getConfigStr(this,"FLAVOR");
    }
}
