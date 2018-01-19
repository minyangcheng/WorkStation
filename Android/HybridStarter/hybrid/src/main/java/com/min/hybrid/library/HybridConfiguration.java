package com.min.hybrid.library;

import android.content.Context;

import com.min.hybrid.library.bridge.api.IBridgeApi;
import com.min.hybrid.library.resource.CheckApiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class HybridConfiguration {

    private Context context;
    private String pageHostUrl;
    private boolean interceptActive;
    private CheckApiHandler checkApiHandler;

    private List<IBridgeApi> customBridgeApiList = new ArrayList<>();

    public HybridConfiguration(Context context) {
        this.context = context.getApplicationContext();
    }

    public String getPageHostUrl() {
        return pageHostUrl;
    }

    public HybridConfiguration setPageHostUrl(String pageHostUrl) {
        this.pageHostUrl = pageHostUrl;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public HybridConfiguration addCustomBridgeApi(IBridgeApi bridgeApi) {
        if (bridgeApi != null) {
            customBridgeApiList.add(bridgeApi);
        }
        return this;
    }

    public List<IBridgeApi> getCustomBridgeApiList() {
        return customBridgeApiList;
    }

    public boolean isInterceptActive() {
        return interceptActive;
    }

    public HybridConfiguration setInterceptActive(boolean interceptActive) {
        this.interceptActive = interceptActive;
        return this;
    }

    public CheckApiHandler getCheckApiHandler() {
        return checkApiHandler;
    }

    public HybridConfiguration setCheckApiHandler(CheckApiHandler checkApiHandler) {
        this.checkApiHandler = checkApiHandler;
        return this;
    }

}
