package com.min.hybrid.library;

import android.content.Context;
import android.text.TextUtils;

import com.min.hybrid.library.resource.ResourceCheck;
import com.min.hybrid.library.resource.ResourceParse;
import com.min.hybrid.library.util.SharePreferenceUtil;

/**
 * Created by minyangcheng on 2018/1/17.
 */

public class HybridManager {

    private static HybridManager hybridManager;

    private HybridConfiguration configuration;
    private ResourceCheck resourceCheck;
    private ResourceParse resourceParse;

    private HybridManager() {
    }

    public static HybridManager getInstance() {
        if (hybridManager == null) {
            synchronized (HybridManager.class) {
                if (hybridManager == null) {
                    hybridManager = new HybridManager();
                }
            }
        }
        return hybridManager;
    }

    public void init(HybridConfiguration configuration) {
        this.configuration = configuration;
        check();
        init();
    }

    private void check() {
        if (configuration == null) {
            throw new RuntimeException("hybrid config can not be null");
        }
        if (configuration.getContext() == null) {
            throw new RuntimeException("hybrid config context can not be null");
        }
        if (TextUtils.isEmpty(configuration.getUpdateUrl())) {
            throw new RuntimeException("hybrid config updateUrl can not be empty");
        }
        if (TextUtils.isEmpty(configuration.getPageHostUrl())) {
            throw new RuntimeException("hybrid config pageUrl can not be empty");
        }
    }

    private void init() {
        resourceCheck = new ResourceCheck(configuration.getContext());
        resourceParse = new ResourceParse();
        setInterceptActive(configuration.isInterceptActive());
    }

    public ResourceParse getResourceParse() {
        check();
        return resourceParse;
    }

    public ResourceCheck getResourceCheck() {
        check();
        return resourceCheck;
    }

    public HybridConfiguration getConfiguration() {
        check();
        return configuration;
    }

    public void checkVersion() {
        getResourceCheck().checkVersion();
    }

    public long prepareJsBundle(Context context) {
        return getResourceParse().prepareJsBundle(context);
    }

    public void setInterceptActive(boolean active) {
        SharePreferenceUtil.setInterceptorActive(configuration.getContext(), active);
    }

    public boolean getInterceptActive() {
        return SharePreferenceUtil.getInterceptorActive(configuration.getContext());
    }

    public String getJsVersionInfo() {
        return SharePreferenceUtil.getVersion(configuration.getContext());
    }

}
