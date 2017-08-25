package com.min.know.gson;

/**
 * Created by minyangcheng on 2016/9/25.
 */
public class DataEntity {

    public String name;
    public int age;
    public OrderStatus status;

    public DataEntity(String name, int age, OrderStatus status) {
        this.name = name;
        this.age = age;
        this.status = status;
    }

}
