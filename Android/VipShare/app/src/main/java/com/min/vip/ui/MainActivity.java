package com.min.vip.ui;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.min.framework.util.PermissionUtil;
import com.min.framework.util.ToastUtils;
import com.min.vip.R;

import java.io.File;

public class MainActivity extends Activity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRefreshLoader(View view) {
        go(RefreshLoaderActivity.class);
    }

    public void goMvp(View view) {
        go(MvpActivity.class);
    }

    public void installPlug(View view) {
        PluginManager pluginManager = PluginManager.getInstance(this);
        File apk = new File(Environment.getExternalStorageDirectory(), "Test.apk");
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startPluginActivity(View view) {
        final String pkg = "com.min.vip.plugin";
        if (PluginManager.getInstance(this).getLoadedPlugin(pkg) == null) {
            Toast.makeText(this, "plugin [com.min.vip.plugin] not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClassName(pkg, "com.min.vip.plugin.MainActivity");
        startActivity(intent);
    }

    public void wakeUpScreen(View view) {
        String[] permissionArr = {Manifest.permission.WAKE_LOCK,
                Manifest.permission.DISABLE_KEYGUARD};
        PermissionUtil.requestPermissions(this, new PermissionUtil.AbstractPermissionAction() {
            @Override
            public void grantSuccess() {
                ToastUtils.showShortToast(getApplicationContext(),"授权成功");
                postDalyWakeUp();
            }

            @Override
            public void grantFail() {
                ToastUtils.showShortToast(getApplicationContext(),"授权失败");
            }
        },permissionArr);
    }

    private void postDalyWakeUp(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wakeUpAndUnlock(MainActivity.this);
            }
        }, 6000);
    }

    public void wakeUpAndUnlock(Context context){
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

    private void go(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

}
