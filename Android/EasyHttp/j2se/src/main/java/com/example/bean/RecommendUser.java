package com.example.bean;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class RecommendUser {

    private String userName;

    private String phone;

    public RecommendUser(String userName, String phone) {
        this.userName = userName;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
