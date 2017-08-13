package com.eh.library.exception;

/**
 * Created by minyangcheng on 2016/8/7.
 */
public class NetError extends Exception{

    public NetError(Throwable throwable) {
        super(throwable);
    }

    public NetError(String detailMessage) {
        super(detailMessage);
    }
}
