package com.cheguo.pos.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cheguo.pos.app.AppConstants;
import com.cheguo.pos.contract.MainContract;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.local.db.delegate.FailReqDaoDelegate;
import com.cheguo.pos.data.model.TransRecord;
import com.cheguo.pos.data.model.status.OrderStatus;
import com.cheguo.pos.data.model.status.PayTypeStatus;
import com.cheguo.pos.data.model.status.SettleTypeStatus;
import com.cheguo.pos.service.UploadFailReqService;
import com.cheguo.pos.util.FormatUtil;
import com.cheguo.pos.util.Util;
import com.cheguo.pos.util.pos.PosUtil;
import com.google.gson.JsonObject;
import com.min.core.util.GsonUtil;
import com.min.core.util.RxUtil;

import java.math.BigDecimal;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class MainPresenter extends MainContract.Presenter {

    private String mNowOrderId;

    private JsonObject mRequestJsonObj;

    @Override
    public void pay(Fragment fragment, double amount, boolean t1Flag) {
        if (amount > 0) {
            BigDecimal bg = new BigDecimal(String.valueOf(amount));
            String amountStr = bg.toPlainString();
            Timber.d("pay amount=%s", amountStr);
            mNowOrderId = Util.generateOrderId();
            if (t1Flag) {
                payT1(fragment, amountStr);
            } else {
                payD0(fragment, amountStr);
            }
        }
    }

    public void payT1(Fragment fragment, String amountStr) {
        mRequestJsonObj = PosUtil.payT1(fragment, amountStr);
        mRequestJsonObj.addProperty(AppConstants.KEY_PAY_TYPE, 1);
        mRequestJsonObj.addProperty(AppConstants.KEY_SETTLE_TYPE, 1);
    }

    public void payD0(Fragment fragment, String amountStr) {
        mRequestJsonObj = PosUtil.payD0(fragment, amountStr);
        mRequestJsonObj.addProperty(AppConstants.KEY_PAY_TYPE, 1);
        mRequestJsonObj.addProperty(AppConstants.KEY_SETTLE_TYPE, 2);
    }


    @Override
    public void handleActivityResult(Context context, Intent data) {
        if (data == null) return;
        JsonObject jsonObject = Util.bundleToJsonObject(data.getExtras());
        Timber.d("pos result: %s", jsonObject.toString());
        uploadResult(context, jsonObject);
    }

    private void uploadResult(Context context, JsonObject jsonObject) {
        if (mRequestJsonObj == null) return;
        TransRecord record = new TransRecord(context);
        record.transType = mRequestJsonObj.get(AppConstants.KEY_TRANS_TYPE).getAsInt();
        record.amount = mRequestJsonObj.get(AppConstants.KEY_AMOUNT).getAsLong();
        record.payType = PayTypeStatus.getValueByStatus(mRequestJsonObj.get(AppConstants.KEY_PAY_TYPE).getAsInt());
        record.settleType = SettleTypeStatus.getValueByStatus(mRequestJsonObj.get(AppConstants.KEY_SETTLE_TYPE).getAsInt());
        mRequestJsonObj = null;
        record.response = jsonObject;
        record.orderId = mNowOrderId;
        String date = jsonObject.has(AppConstants.KEY_DATE) && !jsonObject.get(AppConstants.KEY_DATE).isJsonNull() ? jsonObject.get(AppConstants.KEY_DATE).getAsString() : null;
        String time = jsonObject.has(AppConstants.KEY_TIME) && !jsonObject.get(AppConstants.KEY_TIME).isJsonNull() ? jsonObject.get(AppConstants.KEY_TIME).getAsString() : null;
        record.timestamp = FormatUtil.getTransTime(date, time);
        try {
            record.orderStatus = (jsonObject.get(AppConstants.KEY_TRANS_RESULT) != null && jsonObject.get(AppConstants.KEY_TRANS_RESULT).getAsInt() == 0) ? OrderStatus.SUCCESS : OrderStatus.FAIL;
        } catch (Exception e) {
            record.orderStatus = OrderStatus.FAIL;
        }

        DataManager.getMobileService()
                .addTransInfo(record)
                .compose(RxUtil.handleServerResult())
                .subscribe(o -> {
                    UploadFailReqService.startService(context);
                }, e -> {
                    saveFailUploadRecord(record);
                }, () -> {

                });
    }

    private void saveFailUploadRecord(TransRecord record) {
        FailReqDaoDelegate failReqDaoDelegate = new FailReqDaoDelegate();
        failReqDaoDelegate.save(GsonUtil.toJson(record));
    }

}
