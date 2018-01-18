package com.min.hybrid.library.util;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.List;
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

    public static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return -1;
        }
        version1 = version1.toLowerCase().replaceAll("v", "");
        version2 = version2.toLowerCase().replaceAll("v", "");
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static String joinMapToUrl(String url, Map<String, Object> map) {
        if (url == null || map == null) {
            return url;
        }
        if (map.size() > 0) {
            url += "?";
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            url = url + entry.getKey() + "=" + entry.getValue() + "&";
        }
        if (map.size() > 0) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

}
