package com.min.hybrid.library.bridge.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.bridge.JsCallBackHandler;
import com.min.hybrid.library.bridge.OnBridgeListener;
import com.min.hybrid.library.Constants;
import com.min.hybrid.library.util.Util;

import java.util.Map;

/**
 * Created by minyangcheng on 2018/1/12.
 */

public class OpenActivityApi implements IBridgeApi {

    /**
     * Hybrid.openActivity({destination:'http://www.cheguo.com?name='min'})
     * Hybrid.openActivity({destination:'starter.hybrid.min.com.hybridstarter',name:'min|str',age:'12|int'})
     *
     * @param bridge
     */
    @Override
    public void setMonitor(final Bridge bridge) {
        bridge.on(Constants.BridgeApi.OPEN_ACTIVITY, new OnBridgeListener() {
            @Override
            public void doPerform(Map<String, String> payload, JsCallBackHandler callBackHandler) {
                String destination = payload.get("destination");
                if (!TextUtils.isEmpty(destination)) {
                    Context context = bridge.getContext();
                    if (destination.startsWith("http")||destination.startsWith("file")) {
                        openHybridActivity(context, payload);
                    } else {
                        openNativeActivity(context, payload);
                    }
                }
            }
        });
    }

    private void openHybridActivity(Context context, Map<String, String> payload) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_HYBRID_ACTIVITY);
        intent.putExtra("url", payload.get("destina" +
                "tion"));
        context.startActivity(intent);
    }

    private void openNativeActivity(Context context, Map<String, String> payload) {
        try {
            String destination = payload.get("destination");
            Class clazz = Class.forName(destination);
            Intent intent = new Intent(context, clazz);
            Bundle bundle = new Bundle();
            String key = null;
            String realValue = null;
            for (Map.Entry<String, String> entry : payload.entrySet()) {
                key = entry.getKey();
                realValue = getRealValue(entry.getValue());
                switch (getValueType(entry.getValue())) {
                    case "int":
                        bundle.putInt(key, Util.formatInt(realValue));
                        break;
                    case "long":
                        bundle.putLong(key, Util.formatLong(realValue));
                        break;
                    case "str":
                        bundle.putString(key, realValue);
                        break;
                    case "double":
                        bundle.putDouble(key, Util.formatDouble(realValue));
                        break;
                }
            }
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getValueType(String value) {
        String[] arr = value.split("\\|");
        if (arr.length > 1) {
            return arr[1];
        } else {
            return "str";
        }
    }

    public String getRealValue(String value) {
        String[] arr = value.split("\\|");
        return arr[0];
    }

}
