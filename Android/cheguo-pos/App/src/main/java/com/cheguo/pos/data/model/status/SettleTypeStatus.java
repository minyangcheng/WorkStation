package com.cheguo.pos.data.model.status;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public enum SettleTypeStatus {

    @SerializedName("1")
    T1(1, "t+1"),
    @SerializedName("2")
    DO(2, "d+0"),
    @SerializedName("101")
    UNKNOW(101, "未知");

    private int status;
    private String str;

    private SettleTypeStatus(int status, String str) {
        this.status = status;
        this.str = str;
    }

    public int getStatus() {
        return this.status;
    }

    public String getStr() {
        return this.str;
    }

    public static SettleTypeStatus getValueByStatus(int status) {
        for (SettleTypeStatus type : SettleTypeStatus.values()) {
            if (type.getStatus() == status) {
                return type;
            }
        }
        return UNKNOW;
    }

}
