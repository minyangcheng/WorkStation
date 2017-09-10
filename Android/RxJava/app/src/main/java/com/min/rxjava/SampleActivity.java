package com.min.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.rxjava.util.L;
import com.min.rxjava.util.RetryWithDelay;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class SampleActivity extends AppCompatActivity {

    private static final String TAG = "SampleActivity_Test";

    private boolean isExpired = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
    }

    public Observer getObverser() {
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                L.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.d(TAG, "onError e=%s", e);
            }

            @Override
            public void onNext(String s) {
                L.d(TAG, "onNext s=%s", s);
            }
        };
    }

    @OnClick(R.id.btn_retry_when)
    void clickRetryWhen() {
        Observable.<String>create(subscriber -> {
            L.d(TAG, "onSubscribe call");
            subscriber.onError(new RuntimeException("token is expired"));
        }).retryWhen(new RetryWithDelay(3, 2))
                .subscribe(s -> {
                    L.d(TAG, "onNext=%s", s);
                }, e -> {
                    L.d(TAG, "onError=%s", e.getMessage());
                });
    }

    @OnClick(R.id.btn_token_handle)
    void clickTokenHandle() {
        Observable.create(subscriber -> {
            L.d(TAG, "onSubscribe call");
            if (isExpired) {
                subscriber.onError(new RuntimeException("token is expired"));
            } else {
                subscriber.onNext("A");
                subscriber.onCompleted();
            }
        }).retryWhen(observable -> {
            return observable.<String>flatMap(throwable -> {
                if (throwable.getMessage().equals("token is expired")) {
                    L.d(TAG, "token is expired");
                    isExpired = false;
                    return Observable.just("");
                } else {
                    return Observable.error(throwable);
                }
            });
        }).subscribe(s -> {
            L.d(TAG, "onNext=%s", s);
        }, e -> {
            L.d(TAG, "onError=%s", e.getMessage());
        });
    }

    @OnClick(R.id.btn_handle_error)
    void clickErrorHandle() {
//        Observable.<String>create(subscriber -> {
//            L.d(TAG, "onSubscribe call");
//            subscriber.onError(new RuntimeException("throws is error"));
//        }).compose(stringObservable -> {
//            L.d(TAG, "compose");
//            return stringObservable.onErrorReturn(new Func1<Throwable, String>() {
//                @Override
//                public String call(Throwable throwable) {
//                    throw new RuntimeException("throws is error2");
//                }
//            }).map(s -> {
//                L.d(TAG, "map");
//                return 1;
//            });
//        }).flatMap(observable -> {
//            L.d(TAG, "flat map2");
//            return Observable.just("A");
//        }).subscribe(s -> {
//            L.d(TAG, "onNext=%s", s);
//        }, e -> {
//            L.d(TAG, "onError=%s", e.getMessage());
//        });

        Observable.<String>create(subscriber -> {
            L.d(TAG, "onSubscribe call");
            subscriber.onError(new RuntimeException("throws is error"));
        }).compose(stringObservable -> {
            L.d(TAG, "compose");
            return stringObservable.lift(new Observable.Operator<Integer, String>() {
                @Override
                public Subscriber<? super String> call(Subscriber<? super Integer> subscriber) {
                    return new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            L.d(TAG, "lift onError");
                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(String s) {
                            subscriber.onNext(1);
                        }
                    };
                }
            });
        }).flatMap(observable -> {
            L.d(TAG, "flat map2");
            return Observable.just("A");
        }).subscribe(s -> {
            L.d(TAG, "onNext=%s", s);
        }, e -> {
            L.d(TAG, "onError=%s", e.getMessage());
        });
    }

}
