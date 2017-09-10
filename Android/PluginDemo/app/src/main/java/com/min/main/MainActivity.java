package com.min.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.min.main.util.L;
import com.min.main.util.PluginManager;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void invokeMethodFromPlugin(View view) {
        try {
            Class clazz = PluginManager.getInstance().
                    getDexClassLoader()
                    .loadClass("com.min.plugin.Message");
            Method method = clazz.getMethod("getMessage");
            String mess = (String) method.invoke(clazz.newInstance());
            L.d(TAG, "mess=%s", mess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getResFromPlugin(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_plugin);
        Resources resources = PluginManager.getInstance().getResources();
        Drawable drawable = resources.getDrawable(resources.getIdentifier("ic_progress", "drawable", "com.min.plugin"));
        imageView.setImageDrawable(drawable);
    }

    public void goToDetailActivity(View view) {
        startActivity(new Intent(this, DetailActivity.class));
    }

    public void goToProxy(View view) {
        startActivity(new Intent(this, ProxyActivity.class));
    }

}
