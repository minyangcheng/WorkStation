package com.min.hybrid.library.bridge.api;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.JsCallBackHandler;
import com.min.hybrid.library.bridge.OnBridgeListener;
import com.min.hybrid.library.Constants;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.util.ParseUtil;

import java.util.Map;

/**
 * Created by minyangcheng on 2018/1/12.
 */

public class NativeLogApi implements IBridgeApi {

    /**
     * Hybrid.nativeLog({data:'nihao'})
     *
     * @param bridge
     */
    @Override
    public void setMonitor(final Bridge bridge) {
        bridge.on(Constants.API_NATIVE_LOG, new OnBridgeListener() {
            @Override
            public void doPerform(Map<String, String> payload, JsCallBackHandler callBackHandler) {
                try {
                    String jsonStr = ParseUtil.toJsonString(payload);
                    L.d(Constants.HYBRID_LOG, jsonStr);
                } catch (Exception e) {
                    if (payload != null) {
                        L.d(Constants.HYBRID_LOG, payload.toString());
                    }
                }
            }
        });
    }

}
