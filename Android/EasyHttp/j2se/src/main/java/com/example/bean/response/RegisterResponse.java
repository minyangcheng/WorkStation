package com.example.bean.response;

import com.example.bean.UserBean;
import com.example.http.BaseResponse;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class RegisterResponse extends BaseResponse{

    private String path;

    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
