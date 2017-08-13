package com.eh.library.callback;

import com.eh.library.exception.NetError;

import okhttp3.Response;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public abstract class CallBack<T> {

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onResponse(T response);

    public abstract void onFailure(NetError error);

    public void onProgress(float progress, long total){

    }

    public static class DefaultCallBack extends CallBack{

        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onResponse(Object response) {
        }

        @Override
        public void onFailure(NetError error) {
        }
    }

}
