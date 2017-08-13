package com.min.rxs;

import android.content.Intent;
import android.os.Bundle;

import com.min.rxs.rx.RxStudyTest;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new RxStudyTest().test();
    }

    @OnClick(R.id.btn_test)
    void clickTest(){
        go(TestActivity.class);
    }

    @OnClick(R.id.btn_compare)
    void clickCompare(){
        go(CompareActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
