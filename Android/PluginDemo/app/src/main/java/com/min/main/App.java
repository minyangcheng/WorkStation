package com.min.main;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * Created by minyangcheng on 2017/9/8.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        String path = Environment.getExternalStorageDirectory() + "/Test.apk";
        ResourceUtil.getResource(this, path);
    }
}
