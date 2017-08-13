package com.min.framework.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by cg123 on 2015/8/20.
 */
public class SysUtils {
    /**
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors
     * are used at the project level to set custom fields.
     *
     * @param context   Used to find the correct file
     * @param fieldName The name of the field-to-access
     * @return The value of the field, or {@code null} if the field is not found.
     */
    public static Object getBuildConfigValue(Context context, String fieldName) {
        try {
            int resId = context.getResources().getIdentifier("build_config_package", "string", context.getPackageName());
            // try/catch blah blah
            Class<?> clazz = Class.forName(context.getString(resId) + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (Exception ex){
        }
        return null;
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
        return result==null?null:(String) result;
    }

}
