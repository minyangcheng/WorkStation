package com.cheguo.pos.data.model.status;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/8/25.
 */
public enum OrderStatus {

    @SerializedName("1")
    SUCCESS(1, "交易成功"),
    @SerializedName("2")
    FAIL(2, "交易失败"),
    @SerializedName("3")
    UPDO(3, "交易撤销");

    private int status;
    private String str;

    private OrderStatus(int status, String str) {
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
