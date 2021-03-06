package com.example.proxy.retrofit.net;

import com.example.proxy.retrofit.annotation.Field;
import com.example.proxy.retrofit.annotation.POST;

/**
 * Created by minyangcheng on 2016/9/14.
 */
public interface Api {

    @POST("http://10.10.13.12:8080/dealer/login.json")
    BaseBean<UserInfo> login(@Field("username") String userName, @Field("userpwd") String userPass);

}
