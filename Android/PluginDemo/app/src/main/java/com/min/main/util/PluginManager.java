package com.min.main.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by minyangcheng on 2017/9/8.
 */

public class PluginManager {

    public static PluginManager pluginManager;

    private Context mContext;
    private String mPath;
    private String mOptimizedPath;
    private DexClassLoader mDexClassLoader;
    private Resources mResources;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                if (pluginManager == null) {
                    pluginManager = new PluginManager();
                }
            }
        }
        return pluginManager;
    }

    public void init(Context context, String path) {
        if (context == null) {
            throw new RuntimeException("context can not be null");
        }
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("path can not be empty");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("path is not exit");
        }
        if (!file.isFile()) {
            throw new RuntimeException("path is not file");
        }
        this.mContext = context;
        this.mPath = path;
        mOptimizedPath = context.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();

        initClassLoader();
        initResources();
    }

    private void initClassLoader() {
        mDexClassLoader = new DexClassLoader(mPath, mOptimizedPath, null, getClass().getClassLoader());
    }

    private Resources initResources() {
        AssetManager assetManager = createAssetManager(mPath);
        mResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(),
                mContext.getResources().getConfiguration());
        return mResources;
    }

    private AssetManager createAssetManager(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, path);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resources getResources() {
        return mResources;
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }

}
