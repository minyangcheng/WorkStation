package com.eh.library.http;

import android.content.Context;

import com.eh.library.util.OkHttpUtil;

import okhttp3.OkHttpClient;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class HttpClientConfig {

    private UserInterceptor interceptor;

    private OkHttpClient okHttpClient;

    private DeliveryExecutor delivery;

    public HttpClientConfig(Builder builder){
        okHttpClient=builder.okHttpClient;
        delivery=builder.delivery;
        interceptor=builder.interceptor;
    }

    public UserInterceptor getInterceptor() {
        return interceptor;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public DeliveryExecutor getDelivery() {
        return delivery;
    }

    public static class Builder{
        private UserInterceptor interceptor;

        private OkHttpClient okHttpClient;

        private DeliveryExecutor delivery;

        private Context context;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder setInterceptor(UserInterceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Builder setDelivery(DeliveryExecutor delivery) {
            this.delivery = delivery;
            return this;
        }

        public HttpClientConfig build(){
            initEmptyFieldsWithDefaultValues();
            return new HttpClientConfig(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if(delivery==null){
                delivery=new MainDeliveryExecutor();
            }
            if(okHttpClient==null){
                okHttpClient= OkHttpUtil.getOkHttpClient(true,context.getCacheDir());
            }
        }

    }

}
