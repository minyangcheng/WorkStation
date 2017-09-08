package com.min.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by minyangcheng on 2017/9/8.
 */

public class ResourceUtil {

    public static Resources resources;

    public static Resources getResource(Context context, String path) {
        if (resources != null) {
            return resources;
        }
        AssetManager assetManager = createAssetManager(path);
        resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        return resources;
    }

    private static AssetManager createAssetManager(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                        assetManager, path);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return assetManager;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

}
