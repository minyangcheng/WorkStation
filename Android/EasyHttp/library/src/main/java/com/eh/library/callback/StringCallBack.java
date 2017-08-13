package com.eh.library.callback;

import com.eh.library.base.BaseResponse;
import com.eh.library.exception.UserError;
import com.eh.library.util.GsonUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by minyangcheng on 2016/8/7.
 */
public abstract class StringCallBack extends CallBack<String> {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }

}
