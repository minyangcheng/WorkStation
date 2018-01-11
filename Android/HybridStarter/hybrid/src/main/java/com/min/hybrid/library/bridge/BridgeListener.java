package com.min.hybrid.library.bridge;

import java.util.Map;

public abstract class BridgeListener {

    public Object withPayload;

    public abstract void doPerform(Map<Object, Object> payload);

    public void perform(Map<Object, Object> payload, JsCallBackHandler callBackHandler) {
        doPerform(payload);
        callBackHandler.handle(withPayload);
    }

}
