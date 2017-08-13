package com.eh.library.exception;

import com.eh.library.base.BaseResponse;

/**
 * Created by minyangcheng on 2016/8/7.
 */
public class UserError extends NetError{

    private BaseResponse mBaseResponse;

    public UserError(String detailMessage,BaseResponse baseResponse) {
        super(detailMessage);
        this.mBaseResponse=baseResponse;
    }

    public BaseResponse getBaseResponse() {
        return mBaseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.mBaseResponse = baseResponse;
    }
}
