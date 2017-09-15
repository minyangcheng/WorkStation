package com.min.optimize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MockLeakActivity extends AppCompatActivity {

    private static List<String> CONTAINER_LIST = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_leak);
    }

    public void clickOom(View view) {
        for (int i = 0; i < 500000; i++) {
            CONTAINER_LIST.add("_" + i + "_");
        }
    }

    public void clickLeak(View view) {
        for (int i = 0; i < 5; i++) {
            startTask();
        }
    }

    private void startTask() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(1500);
                        Log.d("MyTest", Thread.currentThread().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
