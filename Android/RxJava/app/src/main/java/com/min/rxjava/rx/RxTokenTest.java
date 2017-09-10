package com.min.rxjava.rx;

import android.text.TextUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by minyangcheng on 2016/9/3.
 */
public class RxTokenTest extends RxTest{

    private String mToken;
    private boolean isExpired;
    private int mRetryTimes=0;

    private String exceptionMess="token is invalid";

    @Override
    public void test(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String userName=getUserName(mToken);
                    subscriber.onNext(userName);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).compose(tokenTransformer())
                .subscribe(getObverser());
    }

    private String getUserName(String token) throws Exception {
        if(TextUtils.isEmpty(token)){
            throw new Exception(exceptionMess);
        }else{
            return "minyangcheng";
        }
    }

    private void getToken() throws Exception{
        mToken=String.valueOf(System.nanoTime());
        throw new Exception("getToken api is can run");
    }

    public Observable.Transformer<String,String> tokenTransformer(){
        return new Observable.Transformer<String, String>() {
            @Override
            public Observable<String> call(Observable<String> tObservable) {
                return tObservable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                logThreadId("retryWhen");
                                if (throwable.getMessage().equals(exceptionMess)) {
                                    return Observable.create(new Observable.OnSubscribe<String>() {
                                        @Override
                                        public void call(Subscriber<? super String> subscriber) {
                                            try {
                                                getToken();
                                                subscriber.onNext("");
                                                subscriber.onCompleted();
                                            } catch (Exception e) {
                                                subscriber.onError(e);
                                            }
                                        }
                                    });
                                } else {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                    }
                });
            }
        };
    }

}
