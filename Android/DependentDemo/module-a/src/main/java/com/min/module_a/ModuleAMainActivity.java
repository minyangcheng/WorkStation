package com.min.module_a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.min.framework.Utils;

public class ModuleAMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_a);
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("A"+"___"+getString(R.string.from)+"___"+getString(R.string.from_2));
    }

}
