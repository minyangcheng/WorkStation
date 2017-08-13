package com.min.framework.util;

import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by minyangcheng on 2016/12/2.
 */

public class PermissionUtil {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
    };

    public static void requestCameraPermission(Activity activity,AbstractPermissionAction action){
        requestPermissions(activity,action, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);
    }

    public static void requestPermissions(Activity activity, Action1<Boolean> action, String... permissions){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(action);
    }

    public static abstract class AbstractPermissionAction implements Action1<Boolean>{

        @Override
        public void call(Boolean aBoolean) {
            if(aBoolean){
                grantSuccess();
            }else{
                grantFail();
            }
        }

        public abstract void grantSuccess();

        public abstract void grantFail();

    }

    public interface AppEnterListener{
        void go();
    }

}
