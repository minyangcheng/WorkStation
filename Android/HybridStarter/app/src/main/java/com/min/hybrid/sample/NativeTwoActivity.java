package com.min.hybrid.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.hybrid.library.util.L;

public class NativeTwoActivity extends AppCompatActivity {

    private static final String TAG = "NativeTwoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_two);
        logIntentData();
    }

    private void logIntentData() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        int age = bundle.getInt("age");
        L.d(TAG, "name=%s,age=%s", name,age);
    }

    public void goToH5(View view) {

    }

}
