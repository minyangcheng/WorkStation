package com.min.rxjava;

import android.content.Intent;
import android.os.Bundle;

import com.min.rxjava.rx.RxStudyTest;
import com.min.rxjava.rx.RxTest;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RxAppCompatActivity {

    private RxTest mRxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mRxText = new RxConverTest();
//        mRxText = new RxCreateTest();
//        mRxText = new RxQuestionTest();
//        mRxText = new RxSubjectTest();
//        mRxText = new RxTokenTest();
        mRxText = new RxStudyTest();
        mRxText.test();
    }

    @OnClick(R.id.btn_rx_view)
    void clickTest() {
        go(RxViewActivity.class);
    }

    @OnClick(R.id.btn_sample)
    void clickGoToSample() {
        go(SampleActivity.class);
    }

    private void go(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
