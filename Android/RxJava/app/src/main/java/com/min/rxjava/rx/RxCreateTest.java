package com.min.rxjava.rx;

import com.min.rxjava.util.L;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxCreateTest extends RxTest{

    @Override
    public void test(){
//        just();
//        from();
//        repeat();
//        repeatWhen();
//        create();
//        defer();
//        range();
//        interval();
//        timer();
//        empty();
//        error();
//        never();
//        doNext();
        handlerError();
    }

    private void handlerError() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int a=0;
                int b=1/a;
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                L.d(mTag,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.e(mTag, "onError " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                L.d(mTag,"onNext "+s);
            }
        });
    }

    public void just(){
        Observable.just("A", "B", "C")
                .subscribe(getObverser());
    }

    public void from(){
        String str[]={"A","B","C"};
        Observable.from(str)
                .subscribe(getObverser());
    }

    public void repeat(){
        Observable.just("A")
                .repeat(3)
                .subscribe(getObverser());
    }

    public void repeatWhen(){
        Observable.just("A")
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return Observable.timer(3, TimeUnit.SECONDS);
                    }
                })
                .repeat(5)
                .subscribe(getObverser());
    }

    public void create(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).subscribe(getObverser());
    }

    public void defer(){
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("A");
            }
        }).subscribe(getObverser());
    }

    public void range(){
        Observable.range(1, 4).subscribe(getObverser());
    }

    public void interval(){
        Observable.interval(2, 5, TimeUnit.SECONDS)
                .subscribe(getObverser());
    }

    public void timer(){
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribe(getLongObverser());
    }

    public void empty(){
        Observable.empty()
                .subscribe(getObverser());
    }

    public void error(){
        Observable.error(new IllegalArgumentException())
                .subscribe(getObverser());
    }

    public void never(){
        Observable.never()
                .subscribe(getObverser());
    }

    public void doNext(){
        Observable.just("A")
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        L.d(mTag,"doOnNext=%s",s);
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                L.d(mTag,"doOnNext=%s","createWorker");
                            }
                        });
                    }
                })
                .subscribe(getObverser());
    }

}
