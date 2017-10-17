package com.cheguo.pos.data.model.status;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minyangcheng on 2017/8/25.
 */
public enum PayTypeStatus {

    @SerializedName("1")
    CARD(1, "刷卡"),
    @SerializedName("2")
    WX(2, "微信扫码"),
    @SerializedName("3")
    ZFB(3, "支付宝扫码"),
    @SerializedName("101")
    UNKNOW(101, "未知");

    private int status;
    private String str;

    private PayTypeStatus(int status, String str) {
        this.status = status;
        this.str = str;
    }

    public int getStatus() {
        return this.status;
    }

    public String getStr() {
        return this.str;
    }

    public static PayTypeStatus getValueByStatus(int status) {
        for (PayTypeStatus type : PayTypeStatus.values()) {
            if (type.getStatus() == status) {
                return type;
            }
        }
        return UNKNOW;
    }

}
