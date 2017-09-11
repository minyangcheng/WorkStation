package com.min.aop_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.min.aop_demo.record.RecordLog;
import com.min.aop_demo.record.RecordPoint;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickPublish(View view) {
        publish();
    }

    @RecordLog("publish")
    @RecordPoint
    private JsonObject publish() {
        Toast.makeText(this, "publish", Toast.LENGTH_SHORT).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("publishId", 101);
        return jsonObject;
    }

    public void clickShare(View view) {
        share();
    }

    @RecordLog("share")
    @RecordPoint
    private JsonObject share() {
        Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content", "weixin share");
        return jsonObject;
    }

    @RecordLog("cancel")
    @RecordPoint
    public void clickCancel(View view) {
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }

}
