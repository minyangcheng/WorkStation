package com.min.hybrid.library;

import android.content.Context;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class HybridConfiguration {

    //="http://10.10.13.117:8080/static/config.json";
    private Context context;
    private String updateUrl;
    private String pageUrl;

    public String getUpdateUrl() {
        return updateUrl;
    }

    public HybridConfiguration setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
        return this;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public HybridConfiguration setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
        return this;
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public Context getContext() {
        return context;
    }

}
