package com.cheguo.pos.util.pos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cheguo.pos.BuildConfig;
import com.cheguo.pos.app.AppConstants;
import com.google.gson.JsonObject;
import com.ys.serviceapi.api.YSSDKManager;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/8/21.
 */

public class PosUtil {

    public static void init(Context context) {
        YSSDKManager.login(context.getApplicationContext(), BuildConfig.YINSHENG_ID);
    }

    public static void destory(Context context) {
        YSSDKManager.logout(context.getApplicationContext());
    }

    /**
     * 付款t1
     *
     * @param fragment
     * @param amount
     */
    public static JsonObject payT1(Fragment fragment, String amount) {
        Intent intent = new Intent();
        long fen = Long.parseLong(PosFormatUtil.changeY2F(amount));
        intent.putExtra("amount", fen);
        Timber.d("fen=%s amount=%s",fen,amount);
        call(fragment, intent, 101);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstants.KEY_TRANS_TYPE, 101);
        jsonObject.addProperty(AppConstants.KEY_AMOUNT, fen);
        return jsonObject;
    }

    /**
     * 付款d0
     *
     * @param fragment
     * @param amount
     */
    public static JsonObject payD0(Fragment fragment, String amount) {
        Intent intent = new Intent();
        long fen = Long.parseLong(PosFormatUtil.changeY2F(amount));
        intent.putExtra("amount", fen);
        Timber.d("fen=%s amount=%s",fen,amount);
        call(fragment, intent, 1009);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstants.KEY_TRANS_TYPE, 1009);
        jsonObject.addProperty(AppConstants.KEY_AMOUNT, fen);
        return jsonObject;
    }

    /**
     * 撤销
     *
     * @param fragment
     * @param traceNo
     */
    public static JsonObject consumeCancel(Fragment fragment, String traceNo) {
        Intent intent = new Intent();
        intent.putExtra("traceNo", traceNo);
        call(fragment, intent, 106);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstants.KEY_TRANS_TYPE, 106);
        jsonObject.addProperty(AppConstants.KEY_TRANS_NO, traceNo);
        return jsonObject;
    }

    /**
     * 打开设置界面
     *
     * @param fragment
     */
    public static void openInitSetting(Fragment fragment) {
        Intent intent = new Intent();
        call(fragment, intent, 1012);
    }

    /**
     * 重新打印
     *
     * @param fragment
     */
    public static void print(Fragment fragment, String traceNo) {
        Intent intent = new Intent();
        intent.putExtra("traceNo", traceNo);
        call(fragment, intent, 2012);
    }

    private static void call(Object object, Intent intent, int transType) {
        intent.setAction("com.ys.smartpos.pay.sdk");
        intent.putExtra("transType", transType);
        if (object instanceof Activity) {
            Activity activity = (Activity) object;
            activity.startActivityForResult(intent, transType);
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            fragment.startActivityForResult(intent, transType);
        }
    }

}
