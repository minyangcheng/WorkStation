package com.example.generic.test;

import com.google.gson.Gson;

/**
 * Created by minyangcheng on 2016/9/21.
 */
public class Client {

    private Gson mGson=new Gson();

    public<T> void request(CallBack<T> callBack){
        Person p=new Person();
        callBack.onSuccess((T) p);
    }

}
