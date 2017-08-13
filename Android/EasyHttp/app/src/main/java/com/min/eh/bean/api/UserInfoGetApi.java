package com.min.eh.bean.api;

import com.eh.library.base.BaseApi;

/**
 * Created by minyangcheng on 2016/8/8.
 */
public class UserInfoGetApi extends BaseApi{

    private String userName;

    private String password;

    public UserInfoGetApi(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getPath() {
        return "https://kyfw.12306.cn/otn/";
    }

    @Override
    public HTTP_TYPE getHttpType() {
        return HTTP_TYPE.GET;
    }
}
