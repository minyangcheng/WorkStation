package com.cheguo.pos;

import com.cheguo.pos.app.App;
import com.facebook.stetho.Stetho;

public class MyDebugApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
