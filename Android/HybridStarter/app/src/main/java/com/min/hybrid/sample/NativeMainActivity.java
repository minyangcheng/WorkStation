package com.min.hybrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.hybrid.library.HybridActivity;

import java.util.HashMap;

public class NativeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_main);
    }

    public void goToH5(View view) {
        Intent intent = new Intent("open.hybrid.activity");
        intent.putExtra(HybridActivity.KEY_PATH, "http://10.10.13.117:8080/#/MainPage");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "闵羊城");
        map.put("age", "1");
        intent.putExtra(HybridActivity.KEY_DATA, map);
        startActivity(intent);
    }

}
