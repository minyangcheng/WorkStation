package com.min.process.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import timber.log.Timber;

public class UploadService extends Service {

    private IBinder mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int calculation(int anInt, int bnInt) throws RemoteException {
            return anInt + bnInt;
        }
    };

    public UploadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }
}
