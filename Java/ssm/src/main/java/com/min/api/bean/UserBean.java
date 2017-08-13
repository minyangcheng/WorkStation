package com.min.api.bean;

import java.io.Serializable;

/**
 * Created by minyangcheng on 2017/6/25.
 */
public class UserBean implements Serializable{

    private int id;
    private String username;
    private String userpass;

    public UserBean(){
    }

    public UserBean(String username, String userpass){
        this.username=username;
        this.userpass=userpass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
}

