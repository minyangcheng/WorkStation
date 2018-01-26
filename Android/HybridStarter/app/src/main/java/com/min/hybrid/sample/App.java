package com.min.hybrid.sample;

import android.app.Application;

import com.min.hybrid.library.HybridConfiguration;
import com.min.hybrid.library.HybridManager;
import com.min.hybrid.library.bean.VersionInfoBean;
import com.min.hybrid.library.net.HttpManager;
import com.min.hybrid.library.resource.CheckApiHandler;
import com.min.hybrid.library.resource.ResourceCheck;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.sample.util.LifecycleCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Util.shouldInit(this)) {
            mInstance = this;
            initHybrid();
            registerLifecycle();
        }
    }

    private void initHybrid() {
        HybridConfiguration configuration = new HybridConfiguration(this)
                .setPageHostUrl("http://10.10.13.117:8080")
//                .setInterceptActive(true)
                .setCheckApiHandler(new CheckApiHandler() {
                    @Override
                    public void checkRequest(ResourceCheck resourceCheck) {
                        checkApiRequest(resourceCheck);
                    }
                });
        HybridManager.getInstance()
                .init(configuration);
    }

    private void checkApiRequest(final ResourceCheck resourceCheck) {
        Request request = new Request.Builder()
                .url("http://10.10.13.117:8080/static/config.json")
                .get()
                .build();
        okhttp3.Call call = HttpManager.getHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                resourceCheck.setCheckApiFailResp(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                VersionInfoBean remoteVersion = ParseUtil.parseObject(response.body().string(), VersionInfoBean.class);
                if(remoteVersion!=null){
                    resourceCheck.setCheckApiSuccessResp(remoteVersion.jsVersion, remoteVersion.md5, remoteVersion.dist);
                }
            }
        });
    }

    private void registerLifecycle() {
        LifecycleCallBack lifecycleManager = new LifecycleCallBack();
        lifecycleManager.register(this).setOnTaskSwitchListenner(new LifecycleCallBack
                .OnTaskSwitchListener() {

            @Override
            public void onTaskSwitchToForeground() {
                HybridManager.getInstance()
                        .checkVersion();
            }

            @Override
            public void onTaskSwitchToBackground() {
            }
        });
    }

    public static App getApplication() {
        return mInstance;
    }

}
