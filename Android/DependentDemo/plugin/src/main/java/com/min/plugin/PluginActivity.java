package com.min.plugin;

import android.os.Bundle;

import org.kymjs.cjframe.CJProxyActivity;

public class PluginActivity extends CJProxyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
    }
}
