package com.min.core.base;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public class AbstractBasePresenter<T extends BaseView> implements BasePresenter<T> {

    private BehaviorSubject mSubject = BehaviorSubject.create();

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        mSubject.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Timber.d("presenter detachview");
            }
        });
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mSubject.onNext("...stop...");
    }

    public Observable bindUtilDetach(){
        return mSubject;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}

