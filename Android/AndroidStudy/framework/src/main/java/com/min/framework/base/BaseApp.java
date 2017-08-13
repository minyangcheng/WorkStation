package com.min.framework.base;

import android.app.Application;
import android.content.Context;

import com.min.framework.util.ImageLoaderWrap;
import com.min.framework.util.L;
import com.min.framework.util.SPUtils;
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

        ToastUtils.init(true);
        SPUtils.init(this);
//        ConfigConstants.DEBUG= SysUtils.isDebug(this);
        ConfigConstants.DEBUG=true;
        L.writeLogs(ConfigConstants.DEBUG);

        //初始化参数
        ConfigConstants.API_URL= "http://10.10.13.117:8081/";
        ImageLoaderWrap.init(this);
    }

    public static Context getContext(){
        return context;
    }

}
