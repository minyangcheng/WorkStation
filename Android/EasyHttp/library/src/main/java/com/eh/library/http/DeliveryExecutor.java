package com.eh.library.http;

import com.eh.library.callback.CallBack;
import com.eh.library.exception.NetError;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public abstract class DeliveryExecutor {

    public abstract void execute(Runnable command);

    public void deliveryError(final NetError error, final CallBack callBack ){
        execute(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(error);
            }
        });
    }

    public void deliveryResponse(final Object responseBean, final CallBack callBack ){
        execute(new Runnable() {
            @Override
            public void run() {
                callBack.onResponse(responseBean);
            }
        });
    }

    public void deliveryProgress(final float progress, final long total, final CallBack callBack ){
        execute(new Runnable() {
            @Override
            public void run() {
                callBack.onProgress(progress,total);
            }
        });
    }

}
