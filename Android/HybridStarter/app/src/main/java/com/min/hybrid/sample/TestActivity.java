package com.min.hybrid.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.hybrid.library.asset.AssetCheckManager;
import com.min.hybrid.library.asset.AssetParseManager;
import com.min.hybrid.library.util.L;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private AssetCheckManager checker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void checkVersion(View view) {
        checker = new AssetCheckManager(this);
        checker.checkVersion();
    }

    public void parseBundle(View view) {
        AssetParseManager manager = new AssetParseManager();
        long time = manager.prepareJsBundle(this);
        L.d(TAG, "waste time=%s", time);
    }

}
