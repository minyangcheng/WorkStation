package com.min.rxs;

import android.os.Bundle;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.min.rxs.rx.RxCreateTest;
import com.min.rxs.rx.RxStudyTest;
import com.min.rxs.rx.RxTest;
import com.min.rxs.util.L;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by minyangcheng on 2016/11/3.
 */
public class TestActivity extends RxAppCompatActivity {

    private static final String TAG="TestActivity_TEST";

    @Bind(R.id.et)
    EditText mEt;

    private RxTest mRxText;
    private Subscription mSubscription;

    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

//        mRxText=new RxConverTest();
//        mRxText=new RxCreateTest();
//        mRxText=new RxQuestionTest();
//        mRxText=new RxSubjectTest();
//        mRxText=new RxTokenTest();
        mRxText=new RxStudyTest();
        mRxText.test();

        initViews();
        start();
    }

    private void initViews() {
        mSubscription = RxTextView.textChanges(mEt)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter( s -> s.toString().trim().length()>0)
                .switchMap(s -> {
                    return Observable.just(s + "_").delay(4,TimeUnit.SECONDS);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    L.d(TAG, "search s=%s", s);
                });
    }

    @OnClick(R.id.btn_rxlife)
    void clickBtnRxLife(){
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(i -> L.d(TAG, "i=%s", i));
    }

    private BehaviorSubject<Integer> behaviorSubject;

    private void start(){
        behaviorSubject=BehaviorSubject.create();
        behaviorSubject.subscribe(i -> L.d(TAG, "action1 i=%s", i));

        Observable.interval(0,2,TimeUnit.SECONDS)
                .takeUntil(behaviorSubject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> L.d(TAG, "start i=%s", i));
    }

    @OnClick(R.id.btn_takeutil)
    void clickBtnTakeUtil(){
        behaviorSubject.onNext(1);
    }

    @OnClick(R.id.btn_takefirst)
    void clickBtnTakeFirst(){
        Observable.just(1,2,3,4,5)
                .takeFirst(i -> i==4?true:false)
                .subscribe(i -> L.d(TAG, "clickBtnTakeFirst i=%s", i));
    }

    @OnClick(R.id.btn_subscription)
    void clickBtnSubscription(){
        Subscription subscription=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("this is test subscription");
                subscriber.onCompleted();
            }
        }).delay(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    L.d(TAG, s);
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        L.d(TAG,"onDestroy...");
        super.onDestroy();
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

}
