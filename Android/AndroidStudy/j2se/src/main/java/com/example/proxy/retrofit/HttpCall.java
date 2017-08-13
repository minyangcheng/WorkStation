package com.example.proxy.retrofit;

import com.example.gson.GsonUtil;
import com.example.proxy.retrofit.net.BaseBean;
import com.example.proxy.retrofit.net.UserInfo;
import com.example.util.L;

import java.util.Map;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class HttpCall {

    private String url;
    private Map<String, String> map;
    private Class<? extends BaseBean> clazz;

    public HttpCall(ServiceMethod serviceMethod) {
        this.url = serviceMethod.getRequestUrl();
        this.map = serviceMethod.getRequestFileds();
        clazz = (Class<? extends BaseBean>) serviceMethod.getReturnType();
    }

    public BaseBean<? extends BaseBean> execute() {
        L.d("url=%s", url);
        L.d("fields=%s", GsonUtil.toJson(map));
        BaseBean<UserInfo> response = new BaseBean<>();
        response.code = 1001;
        response.message = "request success";
        response.data = new UserInfo("minych", 12);
        String responseStr = GsonUtil.toJson(response);
        return GsonUtil.fromJson(responseStr, clazz);
    }

}
