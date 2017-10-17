package com.cheguo.pos.data;

import com.cheguo.pos.data.local.PreferencesHelper;
import com.cheguo.pos.data.remote.MobileService;

/**
 * Created by minyangcheng on 2017/9/19.
 */

public class DataManager {

    private static PreferencesHelper preferencesHelper;
    private static MobileService mobileService;

    public static MobileService getMobileService() {
        if (mobileService == null) {
            mobileService = MobileService.Creator.newMobileService();
        }
        return mobileService;
    }

    public static PreferencesHelper getPreferencesHelper() {
        if (preferencesHelper == null) {
            preferencesHelper = new PreferencesHelper();
        }
        return preferencesHelper;
    }


}
