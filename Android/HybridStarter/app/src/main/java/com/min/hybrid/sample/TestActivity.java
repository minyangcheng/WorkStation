package com.min.hybrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.min.hybrid.library.HybridActivity;
import com.min.hybrid.library.HybridManager;
import com.min.hybrid.library.util.L;

import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private TextView mJsVersionInfoTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mJsVersionInfoTv = findViewById(R.id.tv_version_info);
        mJsVersionInfoTv.setText(HybridManager.getInstance().getJsVersionInfo());
        mJsVersionInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJsVersionInfoTv.setText(HybridManager.getInstance().getJsVersionInfo());
            }
        });
    }

    public void setInterceptActive(View view) {
        boolean active = !HybridManager.getInstance().getInterceptActive();
        HybridManager.getInstance().setInterceptActive(active);
    }

    public void checkVersion(View view) {
        HybridManager.getInstance()
                .checkVersion();
    }

    public void parseBundle(View view) {
        long time = HybridManager.getInstance()
                .prepareJsBundle(this);
        L.d(TAG, "waste time=%s", time);
    }

    public void goToH5(View view) {
        Intent intent = new Intent("open.hybrid.activity");
        intent.putExtra(HybridActivity.KEY_URL, "http://www.baidu.com");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "闵羊城");
        map.put("age", "1");
        intent.putExtra(HybridActivity.KEY_DATA, map);
        startActivity(intent);
    }

}
