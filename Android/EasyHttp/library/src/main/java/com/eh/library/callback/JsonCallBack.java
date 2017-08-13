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
public abstract class JsonCallBack<T extends BaseResponse> extends CallBack<T> {
    private Type type;

    protected JsonCallBack() {
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseStr = response.body().string();
//        responseStr="{\"path\":\"/register\",\"userBean\":{\"userId\":\"1201\",\"userName\":\"min\",\"password\":\"123\",\"areacode\":999},\"code\":1000}";
        final T responseBean = GsonUtil.getGson().fromJson(responseStr, getType());
        if(!validateResponseBean(responseBean)){
            throw new UserError("user level error , pls read the api illustration", responseBean);
        }
        return responseBean;
    }

    protected boolean validateResponseBean(T responseBean){
        return responseBean.isSuccess();
    }

}
