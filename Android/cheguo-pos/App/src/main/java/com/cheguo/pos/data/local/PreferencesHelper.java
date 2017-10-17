package com.cheguo.pos.data.local;

import com.cheguo.pos.app.AppConstants;
import com.min.common.util.SPUtils;

public class PreferencesHelper {

    private SPUtils spUtils;

    public PreferencesHelper() {
        spUtils = SPUtils.getInstance();
    }

    public String getLocationStr() {
        return spUtils.getString(AppConstants.KEY_LOCATION);
    }

    public void putLocationStr(String locationStr) {
        spUtils.put(AppConstants.KEY_LOCATION, locationStr);
    }

    public String getTerminalInfoStr() {
        return spUtils.getString(AppConstants.KEY_TERMINAL_INFO);
    }

    public void putTerminalInfoStr(String terminalInfoStr) {
        spUtils.put(AppConstants.KEY_TERMINAL_INFO, terminalInfoStr);
    }

    public void putTerminalNo(String terminalNo) {
        spUtils.put(AppConstants.KEY_TERMINAL_NO, terminalNo);
    }

    public String getTerminalNo() {
        return spUtils.getString(AppConstants.KEY_TERMINAL_NO);
    }

    public void putMerchantNo(String merchantNo) {
        spUtils.put(AppConstants.KEY_MERCHANT_NO, merchantNo);
    }

    public String getMerchantNo() {
        return spUtils.getString(AppConstants.KEY_MERCHANT_NO);
    }

    public void putMerchantName(String merchantName) {
        spUtils.put(AppConstants.KEY_MERCHANT_NAME, merchantName);
    }

    public String getMerchantName() {
        return spUtils.getString(AppConstants.KEY_MERCHANT_NAME);
    }

}
