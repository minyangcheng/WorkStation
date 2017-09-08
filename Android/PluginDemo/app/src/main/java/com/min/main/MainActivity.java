package com.min.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.min.main.util.L;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DexClassLoader mDexClassLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installPlugin();
    }

    private void installPlugin() {
        try {
            String dexPath = Environment.getExternalStorageDirectory() + "/Test.apk";
            File dexFile = new File(dexPath);
            if (!dexFile.exists()) {
                Toast.makeText(this, "plugin not found", Toast.LENGTH_SHORT).show();
                return;
            }
            String optimizedDirectory = this.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();
            mDexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, getClassLoader().getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用plugin中的类方法
     *
     * @param view
     */
    public void invokeMethodFromPlugin(View view) {
        try {
            Class clazz = mDexClassLoader.loadClass("com.min.plugin.Message");
            Method method = clazz.getMethod("getMessage");
            String mess = (String) method.invoke(clazz.newInstance());
            L.d(TAG, "mess=%s", mess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取plugin中的资源图片，并显示
     *
     * @param view
     */
    public void getResFromPlugin(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_plugin);
        String path = Environment.getExternalStorageDirectory() + "/Test.apk";
        Resources resources = ResourceUtil.getResource(this, path);
        if (resources != null) {
            Drawable drawable = resources.getDrawable(resources.getIdentifier("ic_progress", "drawable", "com.min.plugin"));
            imageView.setImageDrawable(drawable);
        }
    }

    public void goToDetailActivity(View view) {
        startActivity(new Intent(this, DetailActivity.class));
    }

}
