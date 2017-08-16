package com.min.framework.net.download;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpUtil {

    public static OkHttpClient getOkHttpClient(){
        OkHttpClient okClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        return okClient;
    }

}
