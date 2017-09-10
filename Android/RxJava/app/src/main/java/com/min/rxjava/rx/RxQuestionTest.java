package com.min.rxjava.rx;

import com.min.rxjava.util.L;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by minyangcheng on 2016/9/4.
 */
public class RxQuestionTest extends RxTest {

    @Override
    public void test() {
//        threadMap();
//        threadMerge();
        retryWhen();
//        threadSeparateAndMerge();
//        threadChange();
        testDefer();
    }

    private void threadMap(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("create");
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    logThreadId("map");
                    return s + "_";
                }
            }).repeat(2)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.computation())
            .subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    logThreadId("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
//                        logThreadId("onError");
                }

                @Override
                public void onNext(String s) {
//                        logThreadId("onNext s=" + s);
                }
            });
    }

    private void threadMerge(){
        Observable observable_1=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("create_1");
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        });
        Observable observable_2=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("create_2");
                subscriber.onNext("B");
                subscriber.onCompleted();
            }
        });
        Observable.zip(observable_1, observable_2, new Func2<String, String, String>() {
            @Override
            public String call(String o, String o2) {
                return o + o2;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        logThreadId("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        logThreadId("onError");
                    }

                    @Override
                    public void onNext(String s) {
//                        logThreadId("onNext s=" + s);
                    }
                });
    }

    private boolean mIsExpired=true;
    private void retryWhen(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (mIsExpired) {
                    throw new NullPointerException();
                } else {
                    subscriber.onNext("A");
                    subscriber.onCompleted();
                }
            }
        })
            .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                @Override
                public Observable<?> call(Observable<? extends Throwable> observable) {
                    return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            logThreadId("retrywhen...");
                            if (mIsExpired) {
                                return Observable.just("")
                                        .doOnNext(new Action1<String>() {
                                            @Override
                                            public void call(String s) {
                                                mIsExpired = false;
                                            }
                                        });
                            }
                            return Observable.error(throwable);
                        }
                    });
                }
            })
            .subscribe(getObverser());
    }

    private void threadSeparateAndMerge(){
        logThreadId("开始");
        Observable observable_1=Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logThreadId("observable_1");
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Observable observable_2 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logThreadId("observable_2");
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable_1, observable_2, new Func2<Integer,Integer,Integer>() {
            @Override
            public Integer call(Integer o, Integer o2) {
                logThreadId("zip");
                return o+o2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        logThreadId("subscribe o=" + o);
                    }
                });
    }

    private void threadChange(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("create");
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        logThreadId("map");
                        return s+"_";
                    }
                }).observeOn(Schedulers.newThread())
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        logThreadId("flatMap");
                        return Observable.just(1);
                    }
                }).observeOn(Schedulers.newThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        logThreadId("subscribe");
                    }
                });
    }

    private long mLg=12;
    //待解决
    private void testDefer(){
        Observable<Long> observable=Observable.just(mLg);
        Observable<Long> observable_1=Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.just(mLg);
            }
        });
        Observable<Long> observable_2=Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                subscriber.onNext(mLg);
                subscriber.onCompleted();
            }
        });
        mLg=15;
        observable.subscribe(l -> logThreadId("just --"+l));
        observable_1.subscribe(l -> logThreadId("defer --"+l));
        observable_2.subscribe(l -> logThreadId("create --"+l));
    }

}
