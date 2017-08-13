package com.min.framework.net;

import com.min.framework.base.BaseApp;
import com.min.framework.base.ConfigConstants;
import com.min.framework.net.interceptor.ParamInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;

    public static class SingletonHolder{
        public static RetrofitManager retrofitManager=new RetrofitManager();
    }

    private RetrofitManager(){
        initOkHttpClient();
        initRetrofit();
    }

    public static RetrofitManager getInstance(){
        return SingletonHolder.retrofitManager;
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(ConfigConstants.DEBUG?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(new File(BaseApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 30);

        mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ParamInterceptor())
                .addInterceptor(logInterceptor)
//                .addNetworkInterceptor(new RewriteCacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private void initRetrofit(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConfigConstants.API_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
