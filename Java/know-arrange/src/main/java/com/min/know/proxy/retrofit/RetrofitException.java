package com.min.know.proxy.retrofit;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class RetrofitException extends RuntimeException {

    public static final String URL_ERROR="url not find,please use post annotation method or post annotation value can not be empty";
    public static final String METHOD_FIELD_ERROR="method field annotation do not match method parameter";

    public RetrofitException(String message){
        super(message);
    }

}
