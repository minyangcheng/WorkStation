package com.eh.library.http;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class MainDeliveryExecutor extends DeliveryExecutor {

    private Handler mHandler=new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        if(command!=null){
            mHandler.post(command);
        }
    }

}
