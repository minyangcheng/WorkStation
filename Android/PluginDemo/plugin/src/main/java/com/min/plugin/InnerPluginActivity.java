package com.min.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class InnerPluginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_inner_plugin);
//        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(InnerPluginActivity.this, "this is from plugin", Toast.LENGTH_SHORT).show();
//            }
//        });
        Toast.makeText(this, "nihaoma?", Toast.LENGTH_SHORT).show();
    }
}
