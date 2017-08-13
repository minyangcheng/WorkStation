package com.min.know.proxy.retrofit.net;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class BaseBean<T> {

    public int code;
    public String message;
    public T data;

    public boolean isSuccess(){
        return code==10000;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
