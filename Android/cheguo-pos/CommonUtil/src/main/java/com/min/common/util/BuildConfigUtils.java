package com.min.common.util;

import android.content.Context;

import java.lang.reflect.Field;

public class BuildConfigUtils {

    public static Object getBuildConfigValue(Context context, String fieldName) {
        try {
            int resId = context.getResources().getIdentifier("build_config_package", "string", context.getPackageName());
            Class<?> clazz = Class.forName(context.getString(resId) + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Android studio 多个module时，非当前运行的module对家获取BuildConfig.DEBUG都是false
     * 这里通过获取当前应用的context下的BuildConfig来判断才正确
     *
     * @param context
     * @return
     */
    public static boolean isDebug(Context context) {
        Object result = getBuildConfigValue(context, "DEBUG");
        return result != null && ((boolean) result);
    }

    /**
     * 获取当前app的服务器地址，build.gradle里面配置
     *
     * @param context
     * @return
     */
    public static String getConfigStr(Context context, String configStr) {
        Object result = getBuildConfigValue(context, configStr);
        return result == null ? null : (String) result;
    }

}
