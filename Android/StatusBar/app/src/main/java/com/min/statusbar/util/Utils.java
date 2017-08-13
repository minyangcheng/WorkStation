package com.min.statusbar.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

/**
 * Created by minyangcheng on 2016/6/29.
 */
public class Utils {

    private static final String TAG="Utils";

    /**
     * 获取状态栏高度
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static int getColorPrimary(Context context){
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary,
        });
        int colorPrimary = array.getColor(0, 0xFF000000);
        array.recycle();
        return colorPrimary;
    }

    public static void i(String mess){
        Log.i(TAG,mess);
    }

}
