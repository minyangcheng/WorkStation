package com.min.know.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/8/25.
 */
public enum Sex {

    @SerializedName("1")
    MALE(1, "男"),
    @SerializedName("2")
    FEMALE(2, "女");

    private int status;
    private String str;

    private Sex(int status, String str) {
        this.status = status;
        this.str = str;
    }

    public int getStatus() {
        return this.status;
    }

    public String getStr() {
        return this.str;
    }

}
