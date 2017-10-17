package com.min.core.util.download;

import com.min.core.util.HttpsUtil;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DownloadManager {

    private OkHttpClient mOkClient;

    private DownloadManager() {
        mOkClient = getOkHttpClient();
    }

    private static class SingletonHolder {
        public static DownloadManager downloadManager = new DownloadManager();
    }

    public static DownloadManager getInstance() {
        return SingletonHolder.downloadManager;
    }

    public void downloadFile(String url, FileCallBack callback, Object tag) {
        if (tag == null) {
            tag = url;
        }
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        Call call = mOkClient.newCall(request);
        call.enqueue(callback);
    }

    public void downloadFile(String url, FileCallBack callback) {
        String tag = url;
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        Call call = mOkClient.newCall(request);
        call.enqueue(callback);
    }

    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : mOkClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public OkHttpClient getOkHttpClient() {
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);
        OkHttpClient okClient = new OkHttpClient.Builder()
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
                .sslSocketFactory(sslParams.sSLSocketFactory)
                .build();
        return okClient;
    }

}
