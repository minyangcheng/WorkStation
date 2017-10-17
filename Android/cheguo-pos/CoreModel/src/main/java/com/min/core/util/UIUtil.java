package com.min.core.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.min.common.util.ToastUtils;
import com.min.core.R;
import com.min.core.exception.ServerApiException;

/**
 * Created by minyangcheng on 2016/10/14.
 */
public class UIUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void handlerError(Context context, Throwable throwable) {
        if (context == null || throwable == null) return;
        if (throwable instanceof ServerApiException) {
            ServerApiException exception = (ServerApiException) throwable;
            int code = exception.getCode();
            if (code < 0) {
                //异常
                if (code == -1) {
                    ToastUtils.showShort(R.string.http_request_timeout);
                } else {
                    ToastUtils.showShort(R.string.http_request_error);
                }
            } else {
                //服务器返回错误
                String mess = exception.getMessage();
                ToastUtils.showShort(mess);
            }
        }
    }

    public static Handler getMainHandler() {
        return handler;
    }

}
