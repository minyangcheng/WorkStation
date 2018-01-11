package com.min.hybrid.library.util;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

/**
 * Created by minyangcheng on 2018/1/10.
 */

public class Util {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static String format(String formatStr, String defaultStr) {
        return TextUtils.isEmpty(formatStr) ? defaultStr : formatStr;
    }

    public static String format(String formatStr) {
        return format(formatStr, "");
    }

}
