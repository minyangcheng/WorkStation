package com.min.process.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import timber.log.Timber;

public class RemoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
    }

    public void clickPrint(View view) {
        Timber.tag("ShareData").d("counter=%s", ShareData.add());
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "empty");
        Timber.tag("ShareData").d("name=%s", name);
    }

}
