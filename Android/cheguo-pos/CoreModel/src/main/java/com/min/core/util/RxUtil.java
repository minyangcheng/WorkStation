package com.min.core.util;

import android.util.Log;

import com.min.core.Constants;
import com.min.core.bean.BaseBean;
import com.min.core.exception.ServerApiException;

import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxUtil {

    public static <T> Observable.Transformer<BaseBean<T>, T> handleServerResult() {
        return handleServerResult(true);
    }

    public static <T> Observable.Transformer<BaseBean<T>, T> handleServerResult_() {
        return handleServerResult(false);
    }

    private static <T> Observable.Transformer<BaseBean<T>, T> handleServerResult(final boolean isUIThread) {
        return new Observable.Transformer<BaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseBean<T>> tObservable) {
                if (isUIThread) {
                    tObservable = tObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
                return tObservable.lift(new Observable.Operator<T, BaseBean<T>>() {
                    @Override
                    public Subscriber<? super BaseBean<T>> call(final Subscriber<? super T> subscriber) {
                        return new Subscriber<BaseBean<T>>() {
                            @Override
                            public void onCompleted() {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }
                                //http请求返回
                                Timber.tag(Constants.HTTP_LOG).d("response=  %s", Log.getStackTraceString(e));
                                if (e instanceof SocketTimeoutException) {
                                    subscriber.onError(new ServerApiException(-1, e.getMessage()));
                                } else {
                                    subscriber.onError(new ServerApiException(-2, e.getMessage()));
                                }
                            }

                            @Override
                            public void onNext(BaseBean<T> tBaseBean) {
                                if (subscriber.isUnsubscribed()) {
                                    return;
                                }
                                //http请求返回
                                Timber.tag(Constants.HTTP_LOG).d("response=  %s", GsonUtil.toPrettyJson(tBaseBean));
                                if (tBaseBean.isSuccess()) {
                                    subscriber.onNext(tBaseBean.data);
                                } else if (tBaseBean.isSignOut()) {

                                } else {
                                    subscriber.onError(new ServerApiException(tBaseBean));
                                }
                            }

                        };
                    }
                });
            }
        };
    }

    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
