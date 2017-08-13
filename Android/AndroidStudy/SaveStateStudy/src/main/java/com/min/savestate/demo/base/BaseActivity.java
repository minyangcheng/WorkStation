package com.min.savestate.demo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.savestate.demo.util.MyLog;

/**
 * Created by minyangcheng on 2016/5/21.
 */
public class BaseActivity extends AppCompatActivity {

    public String tag;

    public BaseActivity(){
        tag=this.getClass().getSimpleName();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i(tag, "onSaveInstanceState...");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i(tag, "onRestoreInstanceState...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.i(tag, "onCreate...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.i(tag, "onPause...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i(tag, "onResume...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.i(tag, "onDestroy...");
    }
}
