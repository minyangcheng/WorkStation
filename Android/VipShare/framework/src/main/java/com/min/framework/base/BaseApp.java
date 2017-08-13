package com.min.framework.base;

import android.app.Application;
import android.content.Context;
import android.nfc.Tag;

import com.min.framework.BuildConfig;
import com.min.framework.util.L;
import com.min.framework.util.SysUtils;
import com.min.framework.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by minyangcheng on 2016/4/26.
 */
public class BaseApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initImageLoader();
        ToastUtils.init(true);
        boolean isDebug=SysUtils.isDebug(this);
        L.writeLogs(isDebug);

        //初始化参数
        ConfigConstants.API_URL=SysUtils.getConfigStr(this,"API_SERVER_URL");
        ConfigConstants.DEBUG=isDebug;
    }

    public static Context getContext(){
        return context;
    }

    private void initImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

}
