package com.cheguo.pos.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cheguo.pos.app.AppConstants;
import com.cheguo.pos.contract.TransDetailContract;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.local.db.delegate.FailReqDaoDelegate;
import com.cheguo.pos.data.model.TransInfo;
import com.cheguo.pos.data.model.TransRecord;
import com.cheguo.pos.data.model.status.OrderStatus;
import com.cheguo.pos.service.UploadFailReqService;
import com.cheguo.pos.util.FormatUtil;
import com.cheguo.pos.util.Util;
import com.cheguo.pos.util.pos.PosUtil;
import com.google.gson.JsonObject;
import com.min.core.util.GsonUtil;
import com.min.core.util.RxUtil;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class TransDetailPresenter extends TransDetailContract.Presenter {

    private JsonObject mRequestJsonObj;

    @Override
    public void print(Fragment fragment, String transNo) {
        PosUtil.print(fragment, transNo);
    }

    @Override
    public void updo(Fragment fragment, String transNo) {
        mRequestJsonObj = PosUtil.consumeCancel(fragment, transNo);
    }

    @Override
    public void handleActivityResult(Context context, Intent data, TransInfo transInfo) {
        if (data == null) return;
        JsonObject jsonObject = Util.bundleToJsonObject(data.getExtras());
        Timber.d("pos result: %s", jsonObject.toString());
        uploadResult(context, jsonObject, transInfo);
    }

    private void uploadResult(Context context, JsonObject jsonObject, TransInfo transInfo) {
        if (mRequestJsonObj == null) return;
        try {
            if (jsonObject.get(AppConstants.KEY_TRANS_RESULT) == null || jsonObject.get(AppConstants.KEY_TRANS_RESULT).getAsInt() != 0) {
                mRequestJsonObj = null;
                return;
            }
            TransRecord record = new TransRecord(context);
            record.transType = mRequestJsonObj.get(AppConstants.KEY_TRANS_TYPE).getAsInt();
            record.traceNo = mRequestJsonObj.get(AppConstants.KEY_TRANS_NO).getAsString();
            mRequestJsonObj = null;
            record.orderId = transInfo.orderId;
            record.amount = transInfo.amount;
            record.payType = transInfo.payType;
            record.settleType = transInfo.settleType;
            String date = jsonObject.has(AppConstants.KEY_DATE) && !jsonObject.get(AppConstants.KEY_DATE).isJsonNull() ? jsonObject.get(AppConstants.KEY_DATE).getAsString() : null;
            String time = jsonObject.has(AppConstants.KEY_TIME) && !jsonObject.get(AppConstants.KEY_TIME).isJsonNull() ? jsonObject.get(AppConstants.KEY_TIME).getAsString() : null;
            record.timestamp = FormatUtil.getTransTime(date, time);
            record.orderStatus = OrderStatus.UPDO;
            record.response = jsonObject;

            getMvpView().updoSuccess();
            DataManager.getMobileService()
                    .addTransInfo(record)
                    .compose(RxUtil.handleServerResult())
                    .subscribe(o -> {
                        UploadFailReqService.startService(context);
                    }, e -> {
                        saveFailUploadRecord(record);
                    }, () -> {

                    });
        } catch (Exception e) {
        }
    }

    private void saveFailUploadRecord(TransRecord record) {
        FailReqDaoDelegate failReqDaoDelegate = new FailReqDaoDelegate();
        failReqDaoDelegate.save(GsonUtil.toJson(record));
    }

}
