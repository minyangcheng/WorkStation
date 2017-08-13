package com.eh.library.base;

import com.eh.library.util.GsonUtil;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public abstract class BaseApi {

    public abstract String getPath();

    public HTTP_TYPE getHttpType(){
        return HTTP_TYPE.POST;
    }

    public static enum HTTP_TYPE{
        POST,GET
    }

}
