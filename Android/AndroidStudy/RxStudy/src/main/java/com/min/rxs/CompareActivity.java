package com.min.rxs;

import android.os.AsyncTask;
import android.os.Bundle;

import com.min.rxs.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by minyangcheng on 2016/11/3.
 */
public class CompareActivity extends RxAppCompatActivity {

    private static final String TAG="CompareActivity_TEST";

    private Subscription mSubscription;
    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();

    private AsyncTask<Void, Void, String> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_normal)
    void clickNormal(){
        getData_Normal();
    }

    private void getData_Normal(){
        showProgressDialog();
        mTask=new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String s=null;
                try {
                    s= getDataFromServer();
                } catch (Exception e) {
                    s="error:"+e.getMessage();
                }
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                dismissProgressDialog();
                L.d(TAG,"onPostExecute = %s",s);
            }
        };
        mTask.execute();
    }

    @OnClick(R.id.btn_rx)
    void clickCompare(){
        getData_Rx();
    }

    private void getData_Rx(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(getDataFromServer());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgressDialog();
                    }
                }).doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            dismissProgressDialog();
                        }
                    }).subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            L.d(TAG,"onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            L.d(TAG,"onError=%s",e.getMessage());
                        }

                        @Override
                        public void onNext(String s) {
                            L.d(TAG,"onNext = %s",s);
                        }
                    });
    }

    private String getDataFromServer(){
        try {
            Thread.sleep(3000);
            L.d(TAG,"start load more data");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int a=0;
        int b=1/a;
        return "getData success";
    }

    private void showProgressDialog(){
        L.d(TAG, "showProgressDialog...");
    }

    private void dismissProgressDialog(){
        L.d(TAG,"dismissProgressDialog...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTask!=null&&!mTask.isCancelled()){
            mTask.cancel(true);
        }
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

}