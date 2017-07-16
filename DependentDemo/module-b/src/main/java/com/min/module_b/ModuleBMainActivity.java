package com.min.module_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ModuleBMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("B"+"___"+getString(R.string.from)+"___"+getString(R.string.from_2));
    }

}
