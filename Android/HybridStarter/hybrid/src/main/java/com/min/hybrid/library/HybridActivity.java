package com.min.hybrid.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bean.HybridEvent;
import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.util.Constants;
import com.min.hybrid.library.util.EventUtil;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.view.HybridWebView;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import de.greenrobot.event.Subscribe;

public class HybridActivity extends AppCompatActivity {

    private static final String TAG = HybridActivity.class.getSimpleName();
    public static final String KEY_URL = "url";
    public static final String KEY_DATA = "data";

    protected HybridWebView mWebView;
    protected Bridge mBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventUtil.register(this);
        setContentView(R.layout.activity_hybrid);
        mWebView = findViewById(R.id.wv);
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
                        L.d(TAG, "onPageFinished=%s", url);
                    }
                })
                .addDefaultBridgeApi()
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
            Serializable serializable = getIntent().getSerializableExtra(KEY_DATA);
            if (serializable != null) {
                Map<String, Object> map = (Map<String, Object>) serializable;
                if (map.size() > 0) {
                    url += "?";
                }
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    url = url + entry.getKey() + "=" + entry.getValue() + "&";
                }
                if (map.size() > 0) {
                    url = url.substring(0, url.length() - 1);
                }
            }
        }
        loadUrl(url);
    }

    protected void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (intercept(url)) {
                url = getLocalUrl(url);
            }
            mWebView.loadUrl(url);
        }
    }

    protected boolean intercept(String url) {
        if (url.contains("10.10.13.117")) {
            return true;
        }
        return false;
    }

    protected String getLocalUrl(String url) {
        File webAppDir = new File(getFilesDir(), Constants.DIR_WEB_APP);
        File indexFile = new File(webAppDir, "index.html");
        if (!indexFile.exists()) {
            return url;
        }
        String pathTemplate = "file:///%s/index.html#/%s";
        String[] arr = url.split("#");
        String s = "";
        if (arr.length > 1) {
            s = arr[1].substring(1);
        }
        url = String.format(pathTemplate, webAppDir.getAbsolutePath(), s);
        return url;
    }

    @Subscribe
    public void onEvent(HybridEvent event) {
        L.d("Test", "event=%s", ParseUtil.toJsonString(event));
        mBridge.send(event.type, event.data);
    }

}
