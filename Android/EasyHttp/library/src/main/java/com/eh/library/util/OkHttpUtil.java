package com.eh.library.util;

import com.eh.library.http.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class OkHttpUtil {

    private static final String CACHE_DIR="HttpCache";

    public static OkHttpClient getOkHttpClient(boolean canDebug,File cacheDir){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(canDebug?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(new File(cacheDir,CACHE_DIR), 1024 * 1024 * 30);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory)  //设置可以访问所有https网站
                .build();
        return okClient;
    }

}
