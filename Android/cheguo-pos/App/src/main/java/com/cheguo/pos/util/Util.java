package com.cheguo.pos.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.google.gson.JsonObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by minyangcheng on 2017/8/23.
 */

public class Util {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 生成订单号
     *
     * @return
     */
    public static String generateOrderId() {
        String dateStr = simpleDateFormat.format(new Date());
        TerminalInfoUtil.TerminalInfo terminalInfo = TerminalInfoUtil.getInstance().getTerminalInfo();
        String terminalStr = terminalInfo.imei + terminalInfo.serial;
        return dateStr + terminalStr;
    }

    public static JsonObject bundleToJsonObject(Bundle bundle) {
        bundle.putString("eleSign", "  ");  //该字段太长，去掉
        JsonObject jsonObject = new JsonObject();
        Object obj;
        for (String key : bundle.keySet()) {
            obj = bundle.get(key);
            jsonObject.addProperty(key, obj != null ? obj.toString() : null);
        }
        return jsonObject;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 安装APP
     */
    public static void install(Context context, String path) {
        Uri uri = Uri.fromFile(new File(path));
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        context.startActivity(installIntent);
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static void openSettingUi(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    public static void openWifiUi(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

}
