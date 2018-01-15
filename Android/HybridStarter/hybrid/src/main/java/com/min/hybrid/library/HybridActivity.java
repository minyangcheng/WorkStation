package com.min.hybrid.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.view.HybridWebView;

import java.io.Serializable;
import java.util.Map;

public class HybridActivity extends AppCompatActivity {

    private static final String TAG = HybridActivity.class.getSimpleName();
    public static final String KEY_URL = "url";
    public static final String KEY_DATA = "data";

    protected HybridWebView mWebView;
    protected Bridge mBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        mWebView = findViewById(R.id.wv);
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
        render();
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
            mWebView.loadUrl(url);
        }
    }

}
