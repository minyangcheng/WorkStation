package com.min.hybrid.library.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.Payload;
import com.min.hybrid.library.util.Constants;
import com.min.hybrid.library.util.ParseUtil;

import java.net.URI;

public class HybridWebViewClient extends DelegateWebViewClient {

    private Bridge mBridge;
    private WebViewClient mDelegate;

    public HybridWebViewClient(Bridge bridge) {
        mBridge = bridge;
    }

    public void setDelegate(WebViewClient client) {
        mDelegate = client;
    }

    public WebViewClient getDelegate() {
        return mDelegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (getDelegate() != null && getDelegate().shouldOverrideUrlLoading(view, url)) {
            return true;
        }
        try {
            URI uri = new URI(url);
            if (validateScheme(uri)) {
                processUri(view, uri);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateScheme(URI uri) {
        return uri.getScheme().equals(Constants.HYBRID_SCHEME) && !uri.getQuery().equals("");
    }

    public void processUri(WebView view, URI uri) {
        String[] parts = uri.getPath().replaceAll("^\\/", "").split("/");
        String host = uri.getHost();
        Payload payload = ParseUtil.parseObject(uri.getQuery(), Payload.class);
        if (parts.length > 0) {
            if (host.equals("event")) {
                mBridge.triggerEventFromWebView(view, payload);
            } else if (host.equals("callback")) {
                mBridge.triggerCallbackFromWebView(
                        Integer.parseInt(parts[0]));
            }
        }
    }

}