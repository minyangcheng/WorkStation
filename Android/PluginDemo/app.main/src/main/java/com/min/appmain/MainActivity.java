package com.min.appmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setImage(View view) {
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.d1);
        Toast.makeText(this, "setImage", Toast.LENGTH_SHORT).show();
    }

    public void go(View view) {
        startActivity(new Intent(this, SedcondActivity.class));
    }

}
