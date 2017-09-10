package com.min.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.main.util.L;
import com.min.main.util.PluginManager;

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
            field.set(context, PluginManager.getInstance().getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
