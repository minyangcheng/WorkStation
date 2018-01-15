package com.min.hybrid.library.bridge;

import java.util.Map;

public abstract class OnBridgeListener {

    private boolean autoInvokeCallback;

    public OnBridgeListener() {
        autoInvokeCallback = true;
    }

    public OnBridgeListener(boolean autoInvokeCallback) {
        this.autoInvokeCallback = autoInvokeCallback;
    }

    public abstract void doPerform(Map<String, String> payload, JsCallBackHandler callBackHandler);

    public void perform(Map<String, String> payload, JsCallBackHandler callBackHandler) {
        doPerform(payload, callBackHandler);
        if (autoInvokeCallback) {
            callBackHandler.handle(null);
        }
    }

}
