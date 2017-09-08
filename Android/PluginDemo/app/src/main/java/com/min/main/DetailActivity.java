package com.min.main;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.min.main.util.L;

import java.lang.reflect.Field;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getResources().getIdentifier("activity_plugin", "layout", "com.min.plugin");
        L.d(TAG, "layoutId=" + layoutId);
//        View view = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(layoutId);
    }

    @Override
    protected void attachBaseContext(Context context) {
        replaceContextResources(context);
        super.attachBaseContext(context);
    }

    public void replaceContextResources(Context context) {
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            String path = Environment.getExternalStorageDirectory() + "/Test.apk";
            Resources resources = ResourceUtil.getResource(this, path);
            field.set(context, resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
