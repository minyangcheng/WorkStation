package com.min.ws.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;

import butterknife.ButterKnife;

public class MsgViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_view);
        ButterKnife.bind(this);
    }

}
