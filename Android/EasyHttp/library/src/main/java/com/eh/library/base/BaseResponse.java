package com.eh.library.base;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class BaseResponse {

    protected int code;  //假设code==1000为正确码，其余都为错误

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess(){
        return code==1000;
    }
}
