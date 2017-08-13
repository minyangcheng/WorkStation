package com.min.rxs.rx;

import com.min.rxs.util.L;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by minyangcheng on 2016/9/3.
 */
public class RxSubjectTest extends RxTest{

    @Override
    public void test(){
//        behaviorSubject();
        cache();
    }

    private void doComplete(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                logThreadId("doOnComplete");
            }
        }).subscribe();
    }

    //只有订阅以后我们才能接受到发送给它的数值
    private void publishSubject(){
        PublishSubject< Integer> s = PublishSubject.create();
        s.onNext(0);
        s.onNext(1);
        s.subscribe(getIntObverser());
        s.onNext(2);
    }

    //缓存所有推送给它的数据值，当有一个新的订阅者，那么就会为这个新的订阅者从头开始播放原来的一系列事件。
    // 当再有新的事件来时，所有的订阅者也会接受到的。
    //缓存所有数据会受内存大小限制，我们需要限制缓存的大小，
    // ReplaySubject.createWithSize是用来限制缓存大小，而ReplaySubject.createWithTime限制一个对象会被缓存多长时间。
    private void replaySubject(){
        ReplaySubject< Integer> s = ReplaySubject .createWithSize(1);
        s.subscribe(getIntObverser());
        s.onNext(0);
        s.onNext(1);
        s.subscribe(getIntObverser());
        s.onNext(2);
    }

    //BehaviorSubject是只记得最后的值，类似于将缓存大小设为1的ReplaySubject，
    // 创建时会提供一个初始值，这样能保证被订阅时总是立即有一个值可用。
    private void behaviorSubject(){
        BehaviorSubject< Integer> s = BehaviorSubject.create(-1);
        s.subscribe(getIntObverser());
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
    }

    //syncSubject 也会缓存最后的值，区别是，只有等整个事件系列完成时才会发送，否则一个值都不会发送。
    // 一旦完成发送一个单个值。
    private void asyncSubject(){
        AsyncSubject< Integer> s = AsyncSubject.create();
        s.onNext(0);
        s.onNext(1);
        s.subscribe(getIntObverser());
        s.onNext(2);
        s.onCompleted();
    }

    private BehaviorSubject<String> mCacheSubject=BehaviorSubject.create();
    private void cache(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("A");
            }
        }).subscribe(mCacheSubject);
        mCacheSubject.subscribe(getObverser());
    }

}
