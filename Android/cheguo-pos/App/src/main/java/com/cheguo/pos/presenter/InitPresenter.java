package com.cheguo.pos.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cheguo.pos.app.AppConstants;
import com.cheguo.pos.contract.InitContract;
import com.cheguo.pos.data.DataManager;
import com.cheguo.pos.util.pos.PosUtil;
import com.min.common.util.EmptyUtils;
import com.min.common.util.ToastUtils;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class InitPresenter extends InitContract.Presenter {

    @Override
    public boolean check() {
        if (EmptyUtils.isEmpty(DataManager.getPreferencesHelper().getMerchantNo())) {
            ToastUtils.showShort("请先完成初始化设置");
            return false;
        }
        if (EmptyUtils.isEmpty(DataManager.getPreferencesHelper().getMerchantName())) {
            ToastUtils.showShort("请先完成初始化设置");
            return false;
        }
        return true;
    }

    @Override
    public void openInitSetting(Fragment fragment) {
        PosUtil.openInitSetting(fragment);
    }

    @Override
    public void handleActivityResult(Intent data) {
        if (data == null) return;
        String terminalNo = data.getStringExtra(AppConstants.KEY_TERMINAL_NO);
        String merchantNo = data.getStringExtra(AppConstants.KEY_MERCHANT_NO);
        String merchantName = data.getStringExtra(AppConstants.KEY_MERCHANT_NAME);
        if (EmptyUtils.isNotEmpty(terminalNo)) {
            DataManager.getPreferencesHelper().putTerminalNo(terminalNo);
        }
        if (EmptyUtils.isNotEmpty(merchantNo)) {
            DataManager.getPreferencesHelper().putMerchantNo(merchantNo);
        }
        if (EmptyUtils.isNotEmpty(merchantName)) {
            DataManager.getPreferencesHelper().putMerchantName(merchantName);
        }
    }
}
