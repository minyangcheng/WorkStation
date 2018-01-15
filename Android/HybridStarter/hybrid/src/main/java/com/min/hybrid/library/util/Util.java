package com.min.hybrid.library.util;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.Map;

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

    public static String getDataStrFromPayload(Map<String, String> payload) {
        if (payload == null) {
            return null;
        }
        Object obj = payload.get("data");
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static int formatInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public static long formatLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public static double formatDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

}
