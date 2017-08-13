package com.min.rxs.rx;

import com.min.rxs.util.L;

import rx.Observer;

/**
 * Created by minyangcheng on 2016/9/4.
 */
public abstract class RxTest {

    protected String mTag;

    public RxTest(){
        mTag=this.getClass().getSimpleName();
    }

    public abstract void test();

    public Observer getObverser(){
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                L.d(mTag, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.d(mTag,"onError e=%s",e);
            }

            @Override
            public void onNext(String s) {
                L.d(mTag,"onNext s=%s", s);
            }
        };
    }

    public Observer getIntObverser(){
        return new Observer<Integer>() {
            @Override
            public void onCompleted() {
                L.d(mTag, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.d(mTag,"onError e=%s",e);
            }

            @Override
            public void onNext(Integer s) {
                L.d(mTag,"onNext s=%s", s);
            }
        };
    }

    public Observer getLongObverser(){
        return new Observer<Long>() {
            @Override
            public void onCompleted() {
                L.d(mTag, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.d(mTag,"onError e=%s",e);
            }

            @Override
            public void onNext(Long s) {
                L.d(mTag,"onNext s=%s", s);
            }
        };
    }

    protected void logThreadId(String opName){
        L.d(mTag,"opName=%s , threadId=%s ",opName,Thread.currentThread().getName());
    }

}
