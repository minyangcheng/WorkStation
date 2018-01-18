package com.min.hybrid.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.min.hybrid.library.Constants;

public class SharePreferenceUtil {

    public static String getVersion(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                    .SP.NATIVE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(Constants.SP.VERSION, null);
        }
        return null;
    }


    public static void setVersion(Context context, String version) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                    .SP.NATIVE_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(Constants.SP.VERSION, version).apply();
        }
    }


    public static void setDownLoadVersion(Context context, String version) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                    .SP.NATIVE_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(Constants.SP.DOWNLOAD_VERSION, version).apply();
        }
    }

    public static String getDownLoadVersion(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                    .SP.NATIVE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(Constants.SP.DOWNLOAD_VERSION, null);
        }
        return null;
    }


    public static boolean getInterceptorActive(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP.NATIVE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(Constants.SP.INTERCEPTOR_ACTIVE, false);
        }
        return false;
    }

    public static void setInterceptorActive(Context context, boolean active) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP.NATIVE_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(Constants.SP.INTERCEPTOR_ACTIVE, active).apply();
        }
    }

}
