package com.min.eh.bean.api;


import com.eh.library.base.BaseApi;
import com.min.eh.bean.RecommendUser;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class RegisterApi extends BaseApi {

    private String userName;

    private String password;

    private String phone;

    private RecommendUser recommendUser;

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
