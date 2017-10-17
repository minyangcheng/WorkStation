package com.cheguo.pos.data.model;

import com.cheguo.pos.data.model.status.OrderStatus;
import com.cheguo.pos.data.model.status.PayTypeStatus;
import com.cheguo.pos.data.model.status.SettleTypeStatus;

/**
 * Created by minyangcheng on 2017/8/24.
 */

public class TransInfo {

    public String timestamp;
    public String orderId;
    public PayTypeStatus payType;
    public OrderStatus orderStatus;
    public SettleTypeStatus settleType;  //1:t+1 2:d+0

    public String pan;
    public long amount;
    public String traceNo;
    public String referNum;
    public String authCode;
    public String merchantNo;
    public String terminalNo;

}
