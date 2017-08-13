package com.min.statusbar;

import android.os.Bundle;
import android.view.View;

import com.min.statusbar.base.BaseActivity;
import com.min.statusbar.view.StatusBarSpaceAssistLayout;

import java.util.Random;

public class MainActivity extends BaseActivity {

    private StatusBarSpaceAssistLayout mStatusBarLayout;
    private View mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.toolbar);
        mStatusBarLayout= (StatusBarSpaceAssistLayout) findViewById(R.id.sbsl_statusbar);

        findViewById(R.id.btn_change_visiable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mToolbar.getVisibility()==View.VISIBLE){
                    mToolbar.setVisibility(View.GONE);
                }else{
                    mToolbar.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.btn_change_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int color = 0xff000000 | random.nextInt(0xffffff);
                mStatusBarLayout.setStatusBarSpaceColor(color);
            }
        });
    }

}
