package com.min.main;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.min.main.util.PluginManager;

import net.wequick.small.Small;

import java.io.File;

/**
 * Created by minyangcheng on 2017/9/8.
 */

public class App extends Application {

    public App() {
        Small.preSetUp(this);
        Small.setLoadFromAssets(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        String path = Environment.getExternalStorageDirectory() + "/Test.apk";
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            PluginManager.getInstance().init(this, path);
        }
    }
}
