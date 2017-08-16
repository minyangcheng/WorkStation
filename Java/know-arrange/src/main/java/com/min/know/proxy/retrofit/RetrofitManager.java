package com.min.know.proxy.retrofit;

import com.min.know.gson.GsonUtil;
import com.min.know.proxy.retrofit.net.Api;
import com.min.know.proxy.retrofit.net.BaseBean;
import com.min.know.proxy.retrofit.net.UserInfo;
import com.min.know.util.L;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class RetrofitManager {

    public static final void main(String[] args) {
        EasyRetrofit retrofit = new EasyRetrofit();
        Api api = retrofit.create(Api.class);
        BaseBean<UserInfo<String>> bean=api.login("15257178923", "123456a");
        L.d("result=%s",GsonUtil.toJson(bean));
    }

}
