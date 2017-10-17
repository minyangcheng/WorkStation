package com.cheguo.pos.util;

import android.os.Build;

import com.cheguo.pos.data.DataManager;
import com.google.gson.JsonSyntaxException;
import com.min.common.util.DeviceUtils;
import com.min.common.util.EmptyUtils;
import com.min.common.util.PhoneUtils;
import com.min.core.util.GsonUtil;

/**
 * Created by minyangcheng on 2017/8/18.
 */

public class TerminalInfoUtil {

    private static TerminalInfoUtil terminalUtil;

    public static TerminalInfoUtil getInstance() {
        if (terminalUtil == null) {
            synchronized (TerminalInfoUtil.class) {
                if (terminalUtil == null) {
                    terminalUtil = new TerminalInfoUtil();
                }
            }
        }
        return terminalUtil;
    }

    private TerminalInfoUtil() {
    }

    public TerminalInfo getTerminalInfo() {
        String s = DataManager.getPreferencesHelper().getTerminalInfoStr();
        TerminalInfo terminalInfo = null;
        if (EmptyUtils.isNotEmpty(s)) {
            try {
                terminalInfo = GsonUtil.fromJson(s, TerminalInfo.class);
            } catch (JsonSyntaxException e) {
            }
        }
        if (terminalInfo == null) {
            terminalInfo = initTerminalInfo();
            DataManager.getPreferencesHelper().putTerminalInfoStr(GsonUtil.toJson(terminalInfo));
        }
        return terminalInfo;
    }

    private TerminalInfo initTerminalInfo() {
        TerminalInfo terminalInfo = new TerminalInfo();
        terminalInfo.serial = Build.SERIAL;
        terminalInfo.imei = PhoneUtils.getIMEI();
        terminalInfo.imsi = PhoneUtils.getIMSI();
        terminalInfo.phoneType = PhoneUtils.getPhoneType();
        terminalInfo.androidId = DeviceUtils.getAndroidID();
        terminalInfo.macAddress = DeviceUtils.getMacAddress();
        terminalInfo.manufacturer = DeviceUtils.getManufacturer();
        terminalInfo.model = DeviceUtils.getModel();
        return terminalInfo;
    }

    public static class TerminalInfo {
        /**
         * sn串号:f337cacc
         */
        public String serial;
        /**
         * imei号:868239026749718
         */
        public String imei;
        /**
         * imsi号:868239026749123(可能为空)
         */
        public String imsi;
        /**
         * 手机制式:0 手机制式未知 1 手机制式为GSM，移动和联通 2 手机制式为CDMA，电信
         */
        public int phoneType;
        /**
         * AndroidID:6121e66758d980b3
         */
        public String androidId;
        /**
         * MAC地址:bc:3a:ea:bf:09:94
         */
        public String macAddress;
        /**
         * 设备厂商:OPPO
         */
        public String manufacturer;
        /**
         * 设备型号:R7c
         */
        public String model;
    }

}
