package com.min.framework.util;

import android.Manifest;
import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by minyangcheng on 2016/12/2.
 */

public class PermissionUtil {

    private static final String[] APP_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final String[] LOACTION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     * 整个app的权限申请
     *
     * @param activity
     * @param listener
     */
    public static void requestAppPermission(final Activity activity, final AppEnterListener listener) {
        requestPermissions(activity, new PermissionUtil.AbstractPermissionAction() {
            @Override
            public void grantSuccess() {
                if (listener != null) {
                    listener.go();
                }
            }

            @Override
            public void grantFail() {
                if (listener != null) {
                    listener.go();
                }
            }
        }, APP_PERMISSIONS);
    }

    /**
     * 照相机权限检测
     *
     * @param activity
     * @param action
     */
    public static void requestCameraPermission(Activity activity, AbstractPermissionAction action) {
        requestPermissions(activity, action, CAMERA_PERMISSIONS);
    }

    /**
     * 定位权限申请
     *
     * @param activity
     * @param action
     */
    public static void requestLocationPermission(Activity activity, AbstractPermissionAction action) {
        requestPermissions(activity, action, LOACTION_PERMISSIONS);
    }

    /**
     * 读取通讯录权限
     *
     * @param activity
     * @param action
     */
    public static void requestContactPermission(Activity activity, AbstractPermissionAction action) {
        requestPermissions(activity, action, Manifest.permission.READ_CONTACTS);
    }

    public static void requestPermissions(Activity activity, Action1<Boolean> action, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(action);
    }

    public static abstract class AbstractPermissionAction implements Action1<Boolean> {

        private String msg;

        public AbstractPermissionAction() {
            msg = "缺少相应的运行权限，请在设置中授权后重新操作";
        }

        public AbstractPermissionAction(String msg) {
            this.msg = msg;
        }

        @Override
        public void call(Boolean aBoolean) {
            if (aBoolean) {
                grantSuccess();
            } else {
                grantFail();
            }
        }

        public abstract void grantSuccess();

        public void grantFail() {
            ToastUtils.showShort(msg);
        }

    }

    public interface AppEnterListener {
        void go();
    }

}
