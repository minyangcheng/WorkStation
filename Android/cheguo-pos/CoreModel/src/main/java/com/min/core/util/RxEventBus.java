package com.min.core.util;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * A simple event bus built with RxJava
 */
public class RxEventBus {

    public static RxEventBus rxEventBus = new RxEventBus();

    private final PublishSubject<Object> mBusSubject;

    public static RxEventBus getInstance() {
        return rxEventBus;
    }

    private RxEventBus() {
        mBusSubject = PublishSubject.create();
    }

    public void post(Object event) {
        mBusSubject.onNext(event);
    }

    public Observable<Object> observable() {
        return mBusSubject;
    }

    public <T> Observable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.ofType(eventClass);
    }
}