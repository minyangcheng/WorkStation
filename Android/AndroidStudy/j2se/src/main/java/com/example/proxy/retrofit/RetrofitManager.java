package com.example.proxy.retrofit;

import com.example.gson.GsonUtil;
import com.example.proxy.retrofit.net.Api;
import com.example.proxy.retrofit.net.BaseBean;
import com.example.proxy.retrofit.net.UserInfo;
import com.example.util.L;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class RetrofitManager {

    public static final void main(String[] args) {
        EasyRetrofit retrofit = new EasyRetrofit();
        Api api = retrofit.create(Api.class);
        BaseBean<UserInfo> bean=api.login("15257178923", "123456a");
        L.d("result=%s",GsonUtil.toJson(bean));
    }

}
