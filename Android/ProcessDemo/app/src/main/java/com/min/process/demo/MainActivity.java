package com.min.process.demo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("name", "minych")
                .commit();
    }

    public void clickJump(View view) {
        Intent intent = new Intent(this, RemoteActivity.class);
        startActivity(intent);
    }

    public void clickPrint(View view) {
        Timber.tag("ShareData").d("counter=%s", ShareData.add());
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "empty");
        Timber.tag("ShareData").d("name=%s", name);
    }

    public void clickAidl(View view) {
        Intent intent = new Intent();
        intent.setPackage("com.min.process.demo");
        intent.setAction("com.min.process.demo.UploadService");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                try {
                    Timber.d("onServiceConnected=%s", iMyAidlInterface.calculation(1, 3));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

}
