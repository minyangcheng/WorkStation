package com.min.seed.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.min.framework.rx.RxEventBus;
import com.min.framework.util.GsonUtil;
import com.min.seed.app.AppEvent;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/8/18.
 */

public class LocationInfoUtil {

    private static LocationInfoUtil locationInfo;

    public Context mContext;
    public LocationClient mLocationClient = null;

    public BDLocationListener mListener = new MyLocationListener();

    public static LocationInfoUtil getInstance(Context context) {
        if (locationInfo == null) {
            synchronized (LocationInfoUtil.class) {
                if (locationInfo == null) {
                    locationInfo = new LocationInfoUtil(context);
                }
            }
        }
        return locationInfo;
    }

    private LocationInfoUtil(Context context) {
        mContext = context.getApplicationContext();
        initLocation();
        mLocationClient.registerLocationListener(mListener);
    }

    private void initLocation() {
        mLocationClient = new LocationClient(mContext);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        int span = 10000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setEnableSimulateGps(true);
        mLocationClient.setLocOption(option);
    }

    public void startLocation() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    private void stopLocation() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            LocationInfo locationInfo = new LocationInfo(location);
            Timber.d(GsonUtil.toPrettyJson(locationInfo));
            RxEventBus.getInstance().post(AppEvent.newInstance(AppEvent.ChangeEventType.LOCATION));
            stopLocation();
        }

    }

    public class LocationInfo {

        public String country;
        public String countryCode;
        public String province;
        public String city;
        public String cityCode;
        public String district;
        public String street;
        public String streetNumber;
        public String address;
        public double latitude;
        public double longitude;

        public LocationInfo() {
        }

        public LocationInfo(BDLocation bdLocation) {
            this.country = bdLocation.getCountry();
            this.countryCode = bdLocation.getCountryCode();
            this.province = bdLocation.getProvince();
            this.city = bdLocation.getCity();
            this.cityCode = bdLocation.getCityCode();
            this.district = bdLocation.getDistrict();
            this.street = bdLocation.getStreet();
            this.streetNumber = bdLocation.getStreetNumber();
            this.address = bdLocation.getAddress().address;
            this.latitude = bdLocation.getLatitude();
            this.longitude = bdLocation.getLongitude();
        }
    }

}
