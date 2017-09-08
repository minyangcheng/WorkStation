package com.min.aop_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.min.aop_demo.aop.RecordLog;

public class MainActivity extends AppCompatActivity {

    @RecordLog("main ui")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
