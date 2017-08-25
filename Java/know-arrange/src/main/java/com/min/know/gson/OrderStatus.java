package com.min.know.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/8/25.
 */
public enum OrderStatus {

    @SerializedName("1")
    INIT,
    @SerializedName("2")
    SUCCESS,
    @SerializedName("3")
    FAIL,
    @SerializedName("4")
    CANCEL

}
