package com.min.eh.base;

import android.app.Application;

import com.eh.library.base.BaseResponse;
import com.eh.library.http.HttpClient;
import com.eh.library.http.HttpClientConfig;
import com.eh.library.http.MainDeliveryExecutor;
import com.eh.library.http.UserInterceptor;
import com.eh.library.util.OkHttpUtil;
import com.min.eh.BuildConfig;
import com.min.eh.util.L;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final HttpClientConfig config=new HttpClientConfig.Builder(this)
                .setDelivery(new MainDeliveryExecutor())
                .setInterceptor(new UserInterceptor() {
                    @Override
                    public boolean interceptor(BaseResponse baseResponse) {
                        if(baseResponse.getCode()==9999){
                            return true;
                        }
                        return true;
                    }
                })
                .setOkHttpClient(OkHttpUtil.getOkHttpClient(BuildConfig.DEBUG, this.getCacheDir()))
                .build();
        HttpClient.getInstance().init(config);

        L.writeLogs(BuildConfig.DEBUG);
    }
}
