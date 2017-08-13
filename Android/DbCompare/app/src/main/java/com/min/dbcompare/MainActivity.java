package com.min.dbcompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.btn_go_compare)
    void clickBtnGoCompare(){
        go(CompareActivity.class);
    }

    @OnClick(R.id.btn_go_greendao)
    void clickBtnGoGreendao(){
        go(GreendaoActivity.class);
    }

    @OnClick(R.id.btn_upgrade)
    void clickBtnDBUpgrade(){
        go(DbUpgradeActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
