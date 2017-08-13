package com.min.eh.bean;

/**
 * Created by minyangcheng on 2016/8/6.
 */
public class UserBean {

    private String userId;  //注册随机分配用户id

    private String userName;

    private String password;

    private int areacode;  //注册分配地

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getAreacode() {
        return areacode;
    }

    public void setAreacode(int areacode) {
        this.areacode = areacode;
    }
}
