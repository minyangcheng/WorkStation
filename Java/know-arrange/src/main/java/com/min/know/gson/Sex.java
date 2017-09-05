package com.min.databind.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/8/25.
 */
public enum Sex {

    @SerializedName("1")
    MAIN("男"),
    @SerializedName("2")
    SUCCESS("女");

    public String value;

    private Sex(String s) {
        this.value = s;
    }

}
