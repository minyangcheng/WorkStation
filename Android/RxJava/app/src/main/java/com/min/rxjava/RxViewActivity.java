package com.min.rxjava;

import android.os.Bundle;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.min.rxjava.util.L;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by minyangcheng on 2016/11/3.
 */
public class RxViewActivity extends RxAppCompatActivity {

    private static final String TAG = "RxViewActivity_TEST";

    @Bind(R.id.et)
    EditText mEt;

    private BehaviorSubject<Integer> behaviorSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_view);
        ButterKnife.bind(this);

        initViews();
        start();
    }

    private void initViews() {
        RxTextView.textChanges(mEt)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(s -> s.toString().trim().length() > 0)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(s -> {
                    L.d(TAG, "search s=%s", s);
                });
        RxView.clicks(findViewById(R.id.btn_limit_click))
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aVoid -> {
                    L.d(TAG, "click ......."+Thread.currentThread().getName());
                });
    }

    private void start() {
        behaviorSubject = BehaviorSubject.create();
        behaviorSubject.subscribe(i -> L.d(TAG, "action1 i=%s", i));

        Observable.interval(0, 2, TimeUnit.SECONDS)
                .takeUntil(behaviorSubject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> L.d(TAG, "start i=%s", i));
    }

    @OnClick(R.id.btn_takeutil)
    void clickBtnTakeUtil() {
        behaviorSubject.onNext(1);
    }

}
