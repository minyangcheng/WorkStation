package com.min.hybrid.library.bridge;

import android.webkit.WebView;

import com.min.hybrid.library.util.Util;

/**
 * Created by minyangcheng on 2018/1/9.
 */

public class JsCallBackHandler {

    private Bridge bridge;
    private int messageId;

    public JsCallBackHandler(Bridge hybrid, int messageId) {
        this.bridge = hybrid;
        this.messageId = messageId;
    }

    public void handle(final Object payload) {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bridge.triggerCallbackOnWebView(messageId, payload);
            }
        });
    }

}
