package com.min.hybrid.library.bridge;

import android.util.SparseArray;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.util.ParseUtil;
import com.min.hybrid.library.util.Util;
import com.min.hybrid.library.view.HybridWebViewClient;

import java.util.HashMap;
import java.util.Map;

public class Bridge {

    protected int mMessageCount;

    protected Map<String, BridgeListener> mListenerMap = new HashMap<>();

    protected SparseArray<NativeCallback> mCallbackMap = new SparseArray<>();

    private OnValidateListener mOnValidateListener;

    private HybridWebViewClient mWebClient;

    private Bridge() {
        mWebClient = new HybridWebViewClient(this);
    }

    public void send(String type, WebView toWebView) {
        send(type, toWebView, null);
    }

    public void send(String type, WebView toWebView, Object withPayload) {
        send(type, toWebView, withPayload, null);
    }

    public void send(String type, WebView toWebView, NativeCallback callback) {
        send(type, toWebView, null, callback);
    }

    public void send(String type, WebView toWebView, Object withPayload, NativeCallback callback) {
        int messageId = mMessageCount;
        if (callback != null) {
            mCallbackMap.put(messageId, callback);
        }

        String url = null;
        if (withPayload != null && withPayload instanceof String) {
            url = String.format("javascript:Hybrid.trigger(\"%s\", %d, \"%s\")", type, messageId, formatPayload(withPayload));
        } else {
            url = String.format("javascript:Hybrid.trigger(\"%s\", %d, %s)", type, messageId, formatPayload(withPayload));
        }

        toWebView.loadUrl(url);
        ++mMessageCount;
    }

    public void triggerCallbackFromWebView(int messageId) {
        NativeCallback complete = mCallbackMap.get(messageId);
        if (complete != null) {
            complete.call();
            mCallbackMap.remove(messageId);
        }
    }

    public void on(String type, BridgeListener handler) {
        mListenerMap.put(type, handler);
    }

    public void off(String type) {
        mListenerMap.remove(type);
    }

    public void triggerEventFromWebView(final WebView webView, Payload envelope) {
        final int messageId = envelope.id;
        String type = envelope.type;
        if (mListenerMap.containsKey(type)) {
            BridgeListener listener = mListenerMap.get(type);
            listener.perform(envelope.payload, new JsCallBackHandler(this, webView, messageId));
        }
    }

    public void triggerCallbackOnWebView(WebView webView, int messageId, Object withPayload) {
        String url = null;
        if (withPayload != null && withPayload instanceof String) {
            url = String.format("javascript:Hybrid.triggerCallback(\"%d\",\"%s\")", messageId, formatPayload(withPayload));
        } else {
            url = String.format("javascript:Hybrid.triggerCallback(\"%d\",%s)", messageId, formatPayload(withPayload));
        }
        webView.loadUrl(url);
    }

    private String formatPayload(Object withPayload) {
        String payload = withPayload.toString();
        if (withPayload != null && !(withPayload.getClass().isPrimitive()) && !(withPayload instanceof String)) {
            payload = ParseUtil.toJsonString(withPayload);
        }
        payload = Util.format(payload);
        return payload;
    }

    public boolean validate(String host) {
        return mOnValidateListener != null ? mOnValidateListener.validate(host) : true;
    }

    public void setOnValidateListener(OnValidateListener listener) {
        mOnValidateListener = listener;
    }

    public HybridWebViewClient getWebViewClient() {
        return this.mWebClient;
    }

    public void setWebViewClient(WebViewClient client) {
        this.mWebClient.setDelegate(client);
    }

    public void configureWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(this.getWebViewClient());
    }

    public static class Builder {

        private OnValidateListener onValidateListener;
        private WebViewClient webViewClient;

        public Builder setOnValidateListener(OnValidateListener listener) {
            this.onValidateListener = listener;
            return this;
        }

        public Builder setWebViewClient(WebViewClient webViewClient) {
            this.webViewClient = webViewClient;
            return this;
        }

        public Bridge build() {
            Bridge bridge = new Bridge();
            bridge.setOnValidateListener(onValidateListener);
            bridge.setWebViewClient(webViewClient);
            return bridge;
        }

    }
}
