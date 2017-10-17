package com.cheguo.pos.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.min.common.util.ToastUtils;
import com.cheguo.pos.R;
import com.cheguo.pos.util.Util;
import com.min.common.util.FilePathUtil;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateService extends Service {

    private static final String TAG = UpdateService.class.getSimpleName();

    private static final String DOWNLOADING_NOW = "正在下载升级包....";
    private static final String DOWNLOADED_DONE = "升级包下载完成....";
    private static final String DOWNLOADED_FAIL = "升级包下载失败....";

    public static final String ARG_APK_URL = "argApkUrl";
    public static final String ARG_FORCE = "argForce";
    public static final String ARG_MD5 = "argMd5";

    private NotificationManager mNotifyManager;

    private int currentPercent = 0;

    private int NOTIFICATION_ID = 1314;

    private boolean mIsForce;
    private String mApkUrl;
    private String mMd5;

    private File mDestFile;

    public static void startService(Context context, boolean isForce, String apkUrl, String md5) {
        if (Util.isServiceRunning(context, UpdateService.class.getName())) {
            return;
        }
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(ARG_FORCE, isForce);
        intent.putExtra(ARG_APK_URL, apkUrl);
        intent.putExtra(ARG_MD5, md5);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mNotifyManager == null) {
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mIsForce = bundle.getBoolean(ARG_FORCE);
                mApkUrl = bundle.getString(ARG_APK_URL);
                mMd5 = bundle.getString(ARG_MD5);

                String fileName = "CheGuoB.apk";
                mDestFile = new File(FilePathUtil.getCacheRootDir(this), fileName);
                doUpdate();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "service onDestroy()....");
        if (mNotifyManager != null) {
            mNotifyManager.cancel(NOTIFICATION_ID);
        }
    }

    private void doUpdate() {
        showNotification(DOWNLOADING_NOW, 0);
        new Thread() {
            @Override
            public void run() {
                if (mDestFile.exists()) {
                    mDestFile.delete();
                }
//                mApkUrl="http://xiazai.xiazaiba.com/Phone/M/meejian_3.3_XiaZaiBa.apk";
                downloadFile(mApkUrl);
            }
        }.start();
    }

    private void showNotification(String contentText, int progress) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);

        mBuilder.setContentTitle(getString(R.string.app_name))
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.app_name))
                .setAutoCancel(true);

        if (progress > 0 && progress <= 100) {
            mBuilder.setProgress(100, progress, false);
        } else {
            mBuilder.setProgress(0, 0, false);
        }

        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void downloadFile(String fileUrl) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                int length = conn.getContentLength();
                int progress = 0;

                is = conn.getInputStream();
                fos = new FileOutputStream(mDestFile);
                byte[] buff = new byte[1024 * 2];
                int len = 0;
                while ((len = is.read(buff)) != -1) {
                    fos.write(buff, 0, len);
                    progress += len;
                    onProgress(length, progress);
                }

                onFinish();
            } else {
                throw new Exception("response code =" + responseCode);
            }
        } catch (Exception e) {
            L.e(TAG, e);
            onFailure();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onProgress(long total, long current) {
        int tempPercent = (int) ((current / (float) total) * 100);
        if (currentPercent != tempPercent) {
            currentPercent = tempPercent;
            if (currentPercent % 10 == 0) {
                L.d(TAG, "cur=" + currentPercent + "  current=" + current + "  total=" + total);
            }
            Util.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showNotification(DOWNLOADING_NOW, currentPercent);
                }
            });
        }
    }

    public void onFinish() {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showNotification(DOWNLOADED_DONE, 0);
                Util.install(UpdateService.this, mDestFile.getAbsolutePath());

                stopSelf(); // 关闭服务
            }
        });
    }

    public void onFailure() {
        Util.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showNotification(DOWNLOADED_FAIL, 0);
                ToastUtils.showShort("更新失败，请稍后重试...");
                stopSelf(); // 关闭服务
            }
        });
    }

}
