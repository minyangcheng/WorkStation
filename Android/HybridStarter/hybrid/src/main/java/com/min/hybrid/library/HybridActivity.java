package com.min.hybrid.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bean.HybridEvent;
import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.util.EventUtil;
import com.min.hybrid.library.util.FileUtil;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.SharePreferenceUtil;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.library.view.HybridWebView;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import de.greenrobot.event.Subscribe;

public class HybridActivity extends AppCompatActivity {

    public static final String KEY_URL = "url";
    public static final String KEY_DATA = "data";

    protected HybridWebView mWebView;
    protected Bridge mBridge;
    protected HybridConfiguration mHybridConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventUtil.register(this);
        setContentView(R.layout.activity_hybrid);
        mWebView = findViewById(R.id.wv);
        mHybridConfiguration = HybridManager.getInstance().getConfiguration();
        initBridge();
        render();
    }

    private void initBridge() {
        mBridge = new Bridge.Builder()
                .setWebView(mWebView)
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }
                })
                .addDefaultBridgeApi()
                .addCustomBridgeApi(mHybridConfiguration.getCustomBridgeApiList())
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    protected void render() {
        String url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            url = handlePath(url);
        }
        loadUrl(url);
    }

    private String handlePath(String url) {
        if (url.startsWith("/")) {
            url = mHybridConfiguration.getPageHostUrl() + "/#" + url;
        }
        Serializable serializable = getIntent().getSerializableExtra(KEY_DATA);
        if (serializable != null) {
            Map<String, Object> map = (Map<String, Object>) serializable;
            url = Util.joinMapToUrl(url, map);
        }
        if (intercept(url)) {
            url = getLocalUrl(url);
        }
        return url;
    }

    protected void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            L.d(Constants.HYBRID_LOG, "load url=%s", url);
            mWebView.loadUrl(url);
        } else {
            L.d(Constants.HYBRID_LOG, "load empty url");
        }
    }

    protected boolean intercept(String url) {
        return SharePreferenceUtil.getInterceptorActive(this) && url.contains(mHybridConfiguration.getPageHostUrl());
    }

    protected String getLocalUrl(String url) {
        File webAppDir = new File(FileUtil.getBundleDir(this), "bundle");
        File indexFile = new File(webAppDir, "index.html");
        if (!indexFile.exists()) {
            return url;
        }
        String urlTemplate = "file:///%s/index.html#/%s";
        String[] arr = url.split("#");
        String s = "";
        if (arr.length > 1) {
            s = arr[1].substring(1);
        }
        url = String.format(urlTemplate, webAppDir.getAbsolutePath(), s);
        return url;
    }

    @Subscribe
    public void onEvent(HybridEvent event) {
        L.d("Test", "event=%s", ParseUtil.toJsonString(event));
        mBridge.send(event.type, event.data);
    }

}
