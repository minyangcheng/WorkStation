package com.min.hybrid.library.bridge.api;

import com.min.hybrid.library.bean.HybridEvent;
import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.JsCallBackHandler;
import com.min.hybrid.library.bridge.OnBridgeListener;
import com.min.hybrid.library.Constants;
import com.min.hybrid.library.util.EventUtil;

import java.util.Map;

/**
 * Created by minyangcheng on 2018/1/12.
 */

public class PostEventApi implements IBridgeApi {

    /**
     * Hybrid.postEvent({name:'min',age:12})
     *
     * @param bridge
     */
    @Override
    public void setMonitor(final Bridge bridge) {
        bridge.on(Constants.API_POST_EVENT, new OnBridgeListener() {
            @Override
            public void doPerform(Map<String, String> payload, JsCallBackHandler callBackHandler) {
                HybridEvent event = new HybridEvent();
                event.fromUrl = bridge.getWebView().getUrl();
                event.type = payload.get("type");
                event.data = payload;
                EventUtil.post(event);
            }
        });
    }

}
