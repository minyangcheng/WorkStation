package com.min.hybrid.library.bridge.api;

import android.text.TextUtils;
import android.widget.Toast;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.JsCallBackHandler;
import com.min.hybrid.library.bridge.OnBridgeListener;
import com.min.hybrid.library.util.Constants;
import com.min.hybrid.library.util.Util;

import java.util.Map;

/**
 * Created by minyangcheng on 2018/1/12.
 */

public class ToastApi implements IBridgeApi {

    /**
     * Hybrid.toast({data:'nihao'})
     *
     * @param bridge
     */
    @Override
    public void setMonitor(final Bridge bridge) {
        bridge.on(Constants.API_TOAST, new OnBridgeListener() {
            @Override
            public void doPerform(Map<String, String> payload, JsCallBackHandler callBackHandler) {
                String s = Util.getDataStrFromPayload(payload);
                if (!TextUtils.isEmpty(s)) {
                    Toast.makeText(bridge.getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
