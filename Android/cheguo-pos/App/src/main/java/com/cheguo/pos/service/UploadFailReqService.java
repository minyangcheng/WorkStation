package com.cheguo.pos.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.min.common.util.EmptyUtils;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.data.model.TransRecord;
import com.cheguo.pos.data.model.FailReqRecord;
import com.cheguo.pos.data.local.db.delegate.FailReqDaoDelegate;
import com.min.core.util.GsonUtil;
import com.min.core.util.RxUtil;

import java.util.List;

/**
 * Created by minyangcheng on 2016/12/7.
 */

public class UploadFailReqService extends IntentService {

    private FailReqDaoDelegate mFailReqDelegate;

    public static void startService(Context context) {
        Intent intent = new Intent(context, UploadFailReqService.class);
        context.startService(intent);
    }

    public UploadFailReqService() {
        super(UploadFailReqService.class.getName());
        mFailReqDelegate = new FailReqDaoDelegate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<FailReqRecord> reqRecordList = mFailReqDelegate.query();
        if (EmptyUtils.isNotEmpty(reqRecordList)) {
            for (FailReqRecord reqRecord : reqRecordList) {
                uploadFailReq(reqRecord);
            }
        }
    }

    private void uploadFailReq(FailReqRecord failReqRecord) {
        String jsonStr = failReqRecord.getJsonStr();
        TransRecord transRecord = GsonUtil.fromJson(jsonStr, TransRecord.class);
        DataManager.getMobileService()
                .addTransInfo(transRecord)
                .compose(RxUtil.handleServerResult_())
                .subscribe(o -> {
                    deleteWhenReqSuccess(failReqRecord);
                }, e -> {
                }, () -> {
                });
    }

    private void deleteWhenReqSuccess(FailReqRecord reqRecord) {
        mFailReqDelegate.delete(reqRecord);
    }

}
