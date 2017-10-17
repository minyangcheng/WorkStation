package com.min.sample.presenter;

import com.min.core.base.AbstractBasePresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class TestPresenter extends AbstractBasePresenter {

    public void startInt() {
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .takeUntil(bindUtilDetach())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> Timber.d("i=%s", i));
    }

    public void startStr() {
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .map(i -> "s" + i)
                .takeUntil(bindUtilDetach())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> Timber.d("str=%s", i));
    }

}
