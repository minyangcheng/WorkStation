package com.min.sample.data.remote;

import com.min.core.util.HttpsUtil;
import com.min.sample.app.App;
import com.min.sample.BuildConfig;
import com.min.sample.data.remote.interceptor.ParamInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by minyangcheng on 2017/9/19.
 */

public class OkHttpClientManager {

    private OkHttpClient mOkHttpClient;

    private static class SingletonHolder {
        public static OkHttpClientManager okHttpManager = new OkHttpClientManager();
    }

    public static OkHttpClientManager getInstance() {
        return OkHttpClientManager.SingletonHolder.okHttpManager;
    }

    private OkHttpClientManager() {
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 30);

        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);

        mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ParamInterceptor())
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
