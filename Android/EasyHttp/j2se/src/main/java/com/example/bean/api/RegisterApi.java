package com.example.bean.api;

import com.example.bean.RecommendUser;
import com.example.http.BaseApi;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class RegisterApi extends BaseApi {

    private String userName;

    private String password;

    private String phone;

    private RecommendUser recommendUser;

    private String[] strArr={"ab","cd"};

    @Override
    public String getPath() {
        return "http://www.baidu.com";
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RecommendUser getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(RecommendUser recommendUser) {
        this.recommendUser = recommendUser;
    }
}
