package com.min.rxs.rx;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by minyangcheng on 2016/9/3.
 * 给字符串收尾加上下划线
 */
public class StrUnderTrans implements Observable.Transformer<String,String>{
    @Override
    public Observable<String> call(Observable<String> stringObservable) {
        return stringObservable.lift(new Observable.Operator<String, String>() {
            @Override
            public Subscriber<? super String> call(Subscriber<? super String> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(String s) {
                        subscriber.onNext("_"+s+"_");
                    }
                };
            }
        });
    }
}
