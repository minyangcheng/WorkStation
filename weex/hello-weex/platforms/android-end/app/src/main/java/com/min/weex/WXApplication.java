package com.min.weex;

/**
 * Created by minych on 18-3-16.
 */

public class WXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config=new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this,config);
    }
}
