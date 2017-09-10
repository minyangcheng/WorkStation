package com.min.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.main.util.PluginManager;

import java.lang.reflect.Method;

public class ProxyActivity extends AppCompatActivity {

    private Object pluginObj;
    private Method onCreate;
    private Method onStart;
    private Method onResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class clazz = PluginManager.getInstance().getDexClassLoader().loadClass("com.min.plugin.InnerPluginActivity");
            pluginObj = clazz.newInstance();
            onCreate = clazz.getDeclaredMethod("onCreate", new Class[]{Bundle.class});
            onCreate.setAccessible(true);
            onStart = clazz.getDeclaredMethod("onStart");
            onStart.setAccessible(true);
            onResume = clazz.getDeclaredMethod("onResume");
            onResume.setAccessible(true);

            onCreate.invoke(pluginObj, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            onStart.invoke(pluginObj, onStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            onResume.invoke(pluginObj, onResume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
