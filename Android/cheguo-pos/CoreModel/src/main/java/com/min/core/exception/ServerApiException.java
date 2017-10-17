package com.min.core.exception;

import com.min.core.bean.BaseBean;

/**
 * Created by minyangcheng on 2016/10/8.
 */
public class ServerApiException extends RuntimeException{

    private int code;
    private String message;

    public ServerApiException(BaseBean bean) {
        super(bean.message);
        this.code=bean.code;
        this.message=bean.message;
    }

    public ServerApiException(int code,String message) {
        super(message);
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
