package com.eh.library.http;

import com.eh.library.base.BaseResponse;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public abstract class UserInterceptor {

    public boolean filter(Object object){
        if(object instanceof BaseResponse){
            BaseResponse baseResponse= (BaseResponse) object;
            return interceptor(baseResponse);
        }
        return false;
    }

    public abstract boolean interceptor(BaseResponse baseResponse);

}
