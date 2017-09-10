package com.min.rxjava.rx;

import com.min.rxjava.util.GsonUtil;
import com.min.rxjava.util.L;
import com.min.rxjava.util.RetryWithDelay;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by minyangcheng on 2016/12/14.
 */

public class RxStudyTest extends RxTest {

    private static final String TAG="RxStudyTest";

    private Subscriber<String> mSubscriber=new Subscriber<String>() {
        @Override
        public void onCompleted() {
            logThreadId("mSubscriber");
            L.d(TAG,"onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            L.d(TAG,"onError=%s",e.getClass().getName()+"  "+e.getMessage());
        }

        @Override
        public void onNext(String s) {
            L.d(TAG,s);
        }
    };

    private Subscriber<Integer> mIntegerSubscriber=new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
            L.d(TAG,"onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            L.d(TAG,"onError=%s",e.getClass().getName()+"  "+e.getMessage());
        }

        @Override
        public void onNext(Integer s) {
            L.d(TAG,s+"");
        }
    };

    private Subscriber<Long> mLongSubscriber=new Subscriber<Long>() {
        @Override
        public void onCompleted() {
            L.d(TAG,"onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            L.d(TAG,"onError=%s",e.getClass().getName()+"  "+e.getMessage());
        }

        @Override
        public void onNext(Long s) {
            L.d(TAG,s+"");
        }
    };

    @Override
    public void test() {
//        replaySubject();
//        publishSubject();
//        conbineLatest();
//        merge();
//        zip();
//        groupBy();
//        scan();
//        concatMap();
        retryWhen();
//        sample();
//        debounce();
//        timeout();
//        elementAt();
//        distinctUntilChanged();
//        distinct();
//        filter();
//        delay();
//        toSortList();
    }

    private void threadTest(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("call");
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    private void replaySubject(){
        ReplaySubject< Integer> s = ReplaySubject .createWithSize(1);
        s.subscribe(mIntegerSubscriber);
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.subscribe(mIntegerSubscriber);
        s.onNext(3);
    }

    private void publishSubject(){
        PublishSubject< Integer> s = PublishSubject.create();
        s.onNext(0);
        s.subscribe(mIntegerSubscriber);
        s.onNext(1);
        s.subscribe(mIntegerSubscriber);
        s.onNext(2);
    }

    private void conbineLatest(){
        Observable.combineLatest(getObservableCustom(), getObservableLetter(), new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s+"_"+s2;
            }
        }).subscribe(mSubscriber);
    }

    private void zip(){
        Observable.zip(getObservableCustom(), getObservableLetter(), new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s+"_"+s2;
            }
        }).subscribe(mSubscriber);
    }

    private void merge(){
        Observable.merge(getObservableCustom().subscribeOn(Schedulers.io()),getObservableLetter().subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    private void groupBy(){
        Observable<GroupedObservable<String, Integer>> groupItems=Observable.range(1,20)
                .groupBy(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        if(integer%2==0){
                            return "oshu";
                        }else{
                            return "jishu";
                        }
                    }
                });
        Observable.concat(groupItems)
                .subscribe(mIntegerSubscriber);
    }

    private void scan(){
        Observable.just(1,2,3,4)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer sum, Integer item) {
                        L.d(TAG,"sum=%s , item=%s",sum,item);
                        return sum+item;
                    }
                }).subscribe(mIntegerSubscriber);
    }

    private void concatMap(){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                logThreadId("create");
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).concatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        logThreadId("concatMap");
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                logThreadId("concatMap call");
                                subscriber.onNext("{"+integer+"}");
                                subscriber.onCompleted();
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        logThreadId("subscribe call");
                    }
                });
    }

    private boolean isExpired=true;
    private void retryWhenInToken(){
        Observable.create(new Observable.OnSubscribe<String>() {
                      @Override
                      public void call(Subscriber<? super String> subscriber) {
                          L.d(TAG, "call");
                          logThreadId("call");
                          if (isExpired) {
                              subscriber.onError(new RuntimeException("token is expired"));
                          } else {
                              subscriber.onNext("A");
                              subscriber.onCompleted();
                          }
                      }
                    }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                            @Override
                            public Observable<?> call(Observable<? extends Throwable> observable) {
                                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Throwable throwable) {
                                        if (throwable.getMessage().equals("token is expired")) {
                                            L.d(TAG,"token is expired");
                                            isExpired=false;
                                            return Observable.just("");
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                });
                            }
                        }).subscribe(mSubscriber);
    }

    private void retryWhen(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                L.d(TAG, "call");
                logThreadId("call");
                if (isExpired) {
                    subscriber.onError(new RuntimeException("token is expired"));
                } else {
                    subscriber.onNext("A");
                    subscriber.onCompleted();
                }
            }
        }).retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    private void sample(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("a");
                subscriber.onNext("b");
                sleep(2);
                subscriber.onNext("c");
                subscriber.onNext("d");
                subscriber.onCompleted();
            }
        }).sample(1,TimeUnit.SECONDS)
                .subscribe(mSubscriber);
    }

    private void debounce(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("a");
                subscriber.onNext("b");
                sleep(2);
                subscriber.onNext("c");
                subscriber.onNext("d");
                subscriber.onCompleted();
            }
        }).debounce(2,TimeUnit.SECONDS)
                .subscribe(mSubscriber);
    }

    private void timeout(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                sleep(5);
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).timeout(3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(mSubscriber);
    }

    private void elementAt(){
        Observable.just(1,2,3,4,5)
                .elementAt(2)
                .subscribe(mIntegerSubscriber);
    }

    private void distinctUntilChanged(){
        Observable.just(2,1,1,2,2,2,3,3,4)
                .distinctUntilChanged()
                .subscribe(mIntegerSubscriber);
    }

    private void distinct(){
        //采用对象的hashcode和equal方法最是否相同判定
        Observable.just(2,1,1,2,2,2,3,3,4)
                .distinct()
                .subscribe(mIntegerSubscriber);
    }

    private void filter(){
        Observable.range(1,20)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer%2==0;
                    }
                }).subscribe(mIntegerSubscriber);
    }

    private void delay(){
        L.d(TAG,"start delay");
        getObservableCustom().delay(3,TimeUnit.SECONDS)
                .subscribe(mSubscriber);
    }

    private void defer(){
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return getObservableCustom();
            }
        }).subscribe(mSubscriber);
    }

    private void toSortList(){
        Observable.just(2,1,3)
                .toSortedList()
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        L.d(TAG, GsonUtil.toPrettyJson(integers));
                    }
                });
    }

    private Observable<String> getObservableLetter(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("letter_call");
                subscriber.onNext("A");
                subscriber.onNext("B");
                subscriber.onNext("C");
                subscriber.onNext("D");
                subscriber.onCompleted();
            }
        });
    }

    private Observable<Long> getObservableNumber(){
        return Observable.interval(3,TimeUnit.SECONDS);
    }

    private Observable<String> getObservableCustom(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("custom_call");
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onNext("4");
                subscriber.onCompleted();
            }
        });
    }

    private void sleep(int time){
        try {
            Thread.sleep(time*1000);
        } catch (Exception e) {
        }
    }

}
