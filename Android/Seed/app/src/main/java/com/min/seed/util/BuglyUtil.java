package com.min.seed.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.min.seed.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by minyangcheng on 2017/8/4.
 */

public class BuglyUtil {

    public static void init(Context context) {
        if (shouldInit(context)) {
            String packageName = context.getPackageName();
            String processName = getProcessName(android.os.Process.myPid());
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            CrashReport.initCrashReport(context, BuildConfig.BUGLY_APPID, BuildConfig.DEBUG, strategy);
        }
    }

    public static boolean shouldInit(Context ctx) {
        ActivityManager am = ((ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = ctx.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
