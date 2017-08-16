package com.min.know.proxy.retrofit.net;

/**
 * Created by minyangcheng on 2017/8/11.
 */

public class UserInfo<T> {

    public String name;
    public int age;
    public T t;

    public UserInfo() {
    }

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
