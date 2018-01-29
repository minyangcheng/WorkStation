package com.min.hybrid.library.bridge;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bridge.api.IBridgeApi;
import com.min.hybrid.library.bridge.api.NativeLogApi;
import com.min.hybrid.library.bridge.api.OpenActivityApi;
import com.min.hybrid.library.bridge.api.PostEventApi;
import com.min.hybrid.library.bridge.api.ToastApi;
import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.library.view.webview.HybridWebChromeClient;
import com.min.hybrid.library.view.webview.HybridWebViewClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bridge {

    protected Map<String, OnBridgeListener> mListenerMap = new HashMap<>();

    protected SparseArray<NativeCallback> mCallbackMap = new SparseArray<>();

    protected List<IBridgeApi> mBridgeApiList = new ArrayList<>();

    protected HybridWebViewClient mWebViewClient;
    protected HybridWebChromeClient mChromeClient;

    protected WebView mWebView;

    protected int mMessageCount;

    private Bridge(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient) {
        mWebView = webView;
        mWebViewClient = new HybridWebViewClient(this);
        mWebViewClient.setDelegate(webViewClient);
        mChromeClient = new HybridWebChromeClient(this);
        mChromeClient.setDelegate(webChromeClient);
        setClientToWebView();
    }

    private void setClientToWebView() {
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mChromeClient);
    }

    public void send(String type) {
        send(type, null);
    }

    public void send(String type, Object withPayload) {
        send(type, withPayload, null);
    }

    public void send(String type, NativeCallback callback) {
        send(type, null, callback);
    }

    public void send(String type, Object withPayload, NativeCallback callback) {
        int messageId = mMessageCount;
        if (callback != null) {
            mCallbackMap.put(messageId, callback);
        }
        String url = null;
        if (withPayload != null && withPayload instanceof String) {
            url = String.format("javascript:Bridge.trigger(\"%s\", %d, \"%s\")", type, messageId, formatPayload(withPayload));
        } else {
            url = String.format("javascript:Bridge.trigger(\"%s\", %d, %s)", type, messageId, formatPayload(withPayload));
        }

        loadUrlInMainThread(url);
        ++mMessageCount;
    }

    private void loadUrlInMainThread(final String url) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
    }

    public void triggerCallbackFromWebView(int messageId) {
        NativeCallback complete = mCallbackMap.get(messageId);
        if (complete != null) {
            complete.call();
            mCallbackMap.remove(messageId);
        }
    }

    public void on(String type, OnBridgeListener listener) {
        mListenerMap.put(type, listener);
    }

    public void off(String type) {
        mListenerMap.remove(type);
    }

    public void triggerEventFromWebView(final WebView webView, final Envelope envelope) {
        final int messageId = envelope.id;
        String type = envelope.type;
        if (mListenerMap.containsKey(type)) {
            final OnBridgeListener listener = mListenerMap.get(type);
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.perform(envelope.payload, new JsCallBackHandler(Bridge.this, messageId));
                }
            });
        }
    }

    public void triggerCallbackOnWebView(int messageId, Object withPayload) {
        String url = null;
        if (withPayload != null && withPayload instanceof String) {
            url = String.format("javascript:Bridge.triggerCallback(\"%d\",\"%s\")", messageId, formatPayload(withPayload));
        } else {
            url = String.format("javascript:Bridge.triggerCallback(\"%d\",%s)", messageId, formatPayload(withPayload));
        }
        loadUrlInMainThread(url);
    }

    private String formatPayload(Object withPayload) {
        String payload = null;
        if (withPayload != null) {
            payload = withPayload.toString();
            if (!(withPayload.getClass().isPrimitive()) && !(withPayload instanceof String)) {
                payload = ParseUtil.toJsonString(withPayload);
            }
        }
        payload = Util.format(payload);
        if (TextUtils.isEmpty(payload)) {
            payload = "\"\"";
        }
        return payload;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public Context getContext() {
        return mWebView.getContext();
    }

    public void setBridgeApiList(List<IBridgeApi> bridgeApiList) {
        this.mBridgeApiList = bridgeApiList;
        startMonitor();
    }

    private void startMonitor() {
        for (IBridgeApi bridgeApi : mBridgeApiList) {
            bridgeApi.setMonitor(this);
        }
    }

    public static class Builder {

        private WebViewClient webViewClient;
        private WebChromeClient webChromeClient;
        private WebView webView;
        private List<IBridgeApi> bridgeApiList = new ArrayList<>();

        public Builder setWebViewClient(WebViewClient webViewClient) {
            this.webViewClient = webViewClient;
            return this;
        }

        public Builder setWebChromeClient(WebChromeClient webChromeClient) {
            this.webChromeClient = webChromeClient;
            return this;
        }

        public Builder setWebView(WebView webView) {
            this.webView = webView;
            return this;
        }

        public Builder addDefaultBridgeApi() {
            bridgeApiList.add(new OpenActivityApi());
            bridgeApiList.add(new ToastApi());
            bridgeApiList.add(new PostEventApi());
            bridgeApiList.add(new NativeLogApi());
            return this;
        }

        public Builder addCustomBridgeApi(List<IBridgeApi> customBridgeApiList) {
            if (customBridgeApiList != null && customBridgeApiList.size() > 0) {
                bridgeApiList.addAll(customBridgeApiList);
            }
            return this;
        }

        public Bridge build() {
            if (webView == null) {
                throw new RuntimeException("WebView can not be null");
            }
            Bridge bridge = new Bridge(webView, webViewClient, webChromeClient);
            bridge.setBridgeApiList(bridgeApiList);
            return bridge;
        }

    }
}
