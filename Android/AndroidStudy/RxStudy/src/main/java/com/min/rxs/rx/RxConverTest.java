package com.min.rxs.rx;

import com.min.rxs.util.L;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by minyangcheng on 2016/9/3.
 */
public class RxConverTest extends RxTest{

    @Override
    public void test(){
        map();
//        flatMap();
//        distinct();
//        filter();
//        skip();
//        merge();
//        zip();
//        retry();
//        transfrom();
    }

    public void map(){
        String str[]={"A","B","C"};
        Observable.from(str)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "_" + s + "_";
                    }
                })
                .subscribe(getObverser());
    }

    public void flatMap(){
        String str[]={"A","B","C"};
        Observable.from(str)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s);
                    }
                })
                .subscribe(getObverser());
    }

    public void distinct(){
        String str[]={"A","B","C","A","D"};
        Observable.from(str)
                .distinct()
                .subscribe(getObverser());
    }

    public void filter(){
        String str[]={"A","B","C","A","D"};
        Observable.from(str)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("A");
                    }
                })
                .subscribe(getObverser());
    }

    public void skip(){
        String str[]={"A","B","C","A","D"};
        Observable.from(str)
                .skip(2)
                .subscribe(getObverser());
    }

    public void merge(){
        Observable observable_1=Observable.just("A","B");
        Observable observable_2=Observable.just("C","D");
        Observable.merge(observable_1, observable_2)
                .subscribe(getObverser());
    }

    public void zip(){
        Observable observable_1=Observable.just("A","B");
        Observable observable_2=Observable.just("C","D","E");
        Observable.zip(observable_1, observable_2, new Func2<String, String, String>() {
            @Override
            public String call(String o, String o2) {
                return o + o2;
            }
        }).subscribe(getObverser());
    }

    private int mIndex=0;
    public void retry(){
        String str[]={"A","B","C","A","D"};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                logThreadId("call...");
                if (mIndex == 0||mIndex==1) {
                    subscriber.onError(new Exception());
                } else {
                    subscriber.onNext(str[mIndex]);
                    subscriber.onCompleted();
                }
                mIndex++;
            }
        }).retry().subscribe(getObverser());
    }

    public void transfrom(){
        String str[]={"A","B","C","A","D"};
        Observable.from(str)
                .compose(new StrUnderTrans())
                .subscribe(getObverser());
    }

}
