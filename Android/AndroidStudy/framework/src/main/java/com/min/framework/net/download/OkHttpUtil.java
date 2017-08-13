package com.min.framework.net.download;

import com.min.framework.base.ConfigConstants;
import com.min.framework.util.L;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {

    public static OkHttpClient getOkHttpClient(){
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);


        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        L.d("HttpActivity_TEST", "verify=%s , session=%s", hostname,session.toString());
                        return true;
                    }
                })
                .addInterceptor(logInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory)  //设置可以访问所有https网站
                .build();
        return okClient;
    }

}
